package com.fun.driven.development.fun.unified.payments.gateway.processors;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.braintreegateway.exceptions.AuthenticationException;
import com.braintreegateway.exceptions.AuthorizationException;
import com.braintreegateway.exceptions.BraintreeException;
import com.braintreegateway.exceptions.RequestTimeoutException;
import com.braintreegateway.exceptions.TooManyRequestsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleProcessor;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleRequest;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleResult;
import com.fun.driven.development.fun.unified.payments.gateway.util.AmountConverter;
import com.fun.driven.development.fun.unified.payments.gateway.util.ReferenceGenerator;
import com.fun.driven.development.fun.unified.payments.vault.Card;
import com.fun.driven.development.fun.unified.payments.vault.PaymentDetailsVault;
import io.github.jhipster.config.JHipsterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Profiles;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

@Service
public class BraintreeSaleProcessor implements SaleProcessor<TransactionRequest, Pair<Result<Transaction>, String>, BraintreeCredentials> {

    private static final Logger log = LoggerFactory.getLogger(BraintreeSaleProcessor.class);

    @Autowired
    private org.springframework.core.env.Environment env;

    @Autowired
    private PaymentDetailsVault vault;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ReferenceGenerator referenceGenerator;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public BraintreeCredentials loadMerchantCredentials(SaleRequest request) {
        return parseMerchantCredentials(request);
    }

    @Override
    public TransactionRequest toThirdPartyRequest(SaleRequest request) {
        BigDecimal amount = AmountConverter.fromBaseUnitToBigDecimal(request.getAmountInCents(),
                                                                     request.getCurrencyIsoCode());
        Card card = vault.retrieveCard(request.getToken());
        return new TransactionRequest().amount(amount)
                                       .creditCard()
                                       .number(card.getNumber())
                                       .expirationDate(card.getExpirationMMSlashYYYY())
                                       .done();
    }

    @Override
    public Pair<Result<Transaction>, String> thirdPartySale(TransactionRequest request, BraintreeCredentials credentials) {
        //TODO once we have a production ready Braintree account we should use the
        // function loadEnvironment to use the Sandbox or Production environment.
        BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
                                                        credentials.getMerchantId(),
                                                        credentials.getPublicKey(),
                                                        credentials.getPrivateKey());
        Result<Transaction> result = new Result<>();
        String errorMessage = "";
        try {
            result = gateway.transaction().sale(request);
        } catch (AuthenticationException e) {
            log.error("Authentication error while processing Braintree sale", e);
            errorMessage = "Authentication error, please check Braintree merchant credentials";
        } catch (AuthorizationException e) {
            log.error("Authorization error while processing Braintree sale", e);
            errorMessage = "Authorization error, please check Braintree merchant credentials";
        } catch (RequestTimeoutException e) {
            log.error("Timeout error while processing Braintree sale", e);
            errorMessage = "Timeout error, please retry the transaction";
        } catch (TooManyRequestsException e) {
            log.error("Too many requests error while processing Braintree sale", e);
            errorMessage = "Too many requests, please retry the transaction in a few seconds, " +
                "or select a different payment method";
        } catch (BraintreeException e) {
            log.error("Error while processing Braintree sale", e);
            errorMessage = "Braintree is experiencing platform issues, please select a different payment method.";
        }

        return Pair.of(result, errorMessage);
    }

    @Override
    public SaleResult toUnifiedResult(Pair<Result<Transaction>, String> resultOrError) {
        SaleResult result;
        Result<Transaction> thirdPartyResult = resultOrError.getFirst();
        String error = resultOrError.getSecond();

        if (StringUtils.hasText(error)) {
            result = buildErrorResult(error);
        } else if (thirdPartyResult.isSuccess()) {
            result = buildAuthorizedResult(thirdPartyResult);
        } else if (thirdPartyResult.getTransaction() != null) {
            result = buildErrorResult(thirdPartyResult);
        } else {
            result = buildValidationErrorResult(thirdPartyResult);
        }

        if (log.isDebugEnabled()) log.debug("Sale result {}", result);
        return result;
    }

    public BraintreeCredentials parseMerchantCredentials(SaleRequest request) {
        String credentialsJson = request.getMerchantCredentialsJson();

        if (StringUtils.isEmpty(credentialsJson)) {
            log.error("Empty merchant credentials for request {}", request.getReference());
            return new BraintreeCredentials();
        }

        try {
            return mapper.readValue(credentialsJson, BraintreeCredentials.class);
        } catch (IOException e) {
            log.error("Error parsing merchant credentials for request {}", request.getReference());
            return new BraintreeCredentials();
        }
    }

    private SaleResult buildAuthorizedResult(Result<Transaction> thirdPartyResult) {
        Transaction transaction = thirdPartyResult.getTarget();
        SaleResult.ResultCode resultCode = SaleResult.ResultCode.AUTHORIZED;
        String message = retrieveResultMessage(resultCode, transaction.getId());
        return new SaleResult().setReference(referenceGenerator.generate())
                               .setResultCode(resultCode)
                               .setResultDescription(message);
    }

    private SaleResult buildErrorResult(String thirdPartyError) {
        SaleResult.ResultCode resultCode = SaleResult.ResultCode.ERROR;
        String message = retrieveResultMessage(resultCode, thirdPartyError);
        return new SaleResult().setReference(referenceGenerator.generate())
                               .setResultCode(resultCode)
                               .setResultDescription(message);
    }

    private SaleResult buildErrorResult(Result<Transaction> thirdPartyResult) {
        Transaction transaction = thirdPartyResult.getTransaction();
        SaleResult.ResultCode resultCode = SaleResult.ResultCode.ERROR;
        //TODO instead of directly returning the third party error code, create a map to internal,
        // curated error codes
        String message = retrieveResultMessage(resultCode, transaction.getProcessorResponseCode());
        return new SaleResult().setReference(referenceGenerator.generate())
                               .setResultCode(resultCode)
                               .setResultDescription(message);
    }

    private SaleResult buildValidationErrorResult(Result<Transaction> thirdPartyResult) {
        String validationMessage = "";
        for (ValidationError error : thirdPartyResult.getErrors().getAllDeepValidationErrors()) {
            validationMessage = error.getAttribute() + " " + error.getMessage();
            break; // We only return the first validation error.
        }
        //TODO instead of directly returning the third party validation message, create a map to internal,
        // curated validation errors
        SaleResult.ResultCode resultCode = SaleResult.ResultCode.VALIDATION_ERROR;
        String message = retrieveResultMessage(resultCode, validationMessage);
        return new SaleResult().setReference(referenceGenerator.generate())
                               .setResultCode(resultCode)
                               .setResultDescription(message);
    }

    private String retrieveResultMessage(SaleResult.ResultCode resultCode, String param) {
        String messageKey = "unified.sale.result." + resultCode.name().toLowerCase();
        String[] params = null;
        if (param != null) {
             params = new String[]{param};
        }
        return messageSource.getMessage(messageKey, params, Locale.ENGLISH);
    }

    //TODO Unused method, but will be needed when the project goes live processing real transactions
    private Environment loadEnvironment() {
        Environment environment;
        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_PRODUCTION))) {
            environment = Environment.PRODUCTION;
        } else {
            environment = Environment.SANDBOX;
        }
        return environment;
    }
}
