package com.fun.driven.development.fun.unified.payments.gateway.processors;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleProcessor;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleRequest;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleResult;
import com.fun.driven.development.fun.unified.payments.gateway.util.AmountConverter;
import com.fun.driven.development.fun.unified.payments.vault.Card;
import com.fun.driven.development.fun.unified.payments.vault.PaymentDetailsVault;
import io.github.jhipster.config.JHipsterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Profiles;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

public class BraintreeSaleProcessor implements SaleProcessor<TransactionRequest, Result<Transaction>, BraintreeCredentials> {

    private static final Logger log = LoggerFactory.getLogger(BraintreeSaleProcessor.class);

    @Autowired
    private org.springframework.core.env.Environment env;

    @Autowired
    private PaymentDetailsVault vault;

    @Autowired
    private MessageSource messageSource;

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
    public Result<Transaction> thirdPartySale(TransactionRequest request, BraintreeCredentials credentials) {
        //TODO once we have a production ready Braintree account we should use the
        // function loadEnvironment to use the Sandbox or Production environment.
        BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
                                                        credentials.getMerchantId(),
                                                        credentials.getPublicKey(),
                                                        credentials.getPrivateKey());
        return gateway.transaction().sale(request);
    }

    @Override
    public SaleResult toUnifiedResult(Result<Transaction> thirdPartyResult) {
        SaleResult result;

        if (thirdPartyResult.isSuccess()) {
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
        return new SaleResult().setResultCode(resultCode)
                               .setResultDescription(message);
    }

    private SaleResult buildErrorResult(Result<Transaction> thirdPartyResult) {
        Transaction transaction = thirdPartyResult.getTransaction();
        SaleResult.ResultCode resultCode = SaleResult.ResultCode.ERROR;
        //TODO instead of directly returning the third party error code, create a map to internal,
        // curated error codes
        String message = retrieveResultMessage(resultCode, transaction.getProcessorResponseCode());
        return new SaleResult().setResultCode(resultCode)
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
        return new SaleResult().setResultCode(resultCode)
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
