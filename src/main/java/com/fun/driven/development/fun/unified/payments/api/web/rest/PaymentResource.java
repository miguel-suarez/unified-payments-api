package com.fun.driven.development.fun.unified.payments.api.web.rest;

import com.fun.driven.development.fun.unified.payments.api.service.*;
import com.fun.driven.development.fun.unified.payments.api.service.dto.*;
import com.fun.driven.development.fun.unified.payments.api.web.rest.mapper.TransactionDTOMapper;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleRequestVM;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleResultVM;
import com.fun.driven.development.fun.unified.payments.gateway.core.AvailableProcessor;
import com.fun.driven.development.fun.unified.payments.gateway.core.PaymentGateway;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleRequest;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleResult;
import com.fun.driven.development.fun.unified.payments.gateway.util.ReferenceGenerator;
import com.fun.driven.development.fun.unified.payments.vault.service.StrongCryptography;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Api(value = "token")
@RequestMapping("/api")
public class PaymentResource {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private PaymentMethodCredentialService credentialService;

    @Autowired
    private TransactionDTOMapper transactionMapper;

    @Autowired
    private UnifiedPaymentTokenService tokenService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private ReferenceGenerator referenceGenerator;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StrongCryptography strongCryptography;

    /**
     * POST /v1/unified/payments/sale : Submits a sale payment request
     *
     * @param merchantReference  (required)
     * @param request Details of the sale payment (required)
     * @return Successfully created (status code 201)
     *         or Invalid input data supplied (status code 400)
     *         or Authentication information is missing or invalid (status code 401)
     *         or User isn&#39;t allow to perform the requested action (status code 403)
     *         or Sorry we can&#39;t process this request at the moment (status code 500)
     */
    @ApiOperation(value = "Submits a sale payment request", nickname = "submitSale",
                  response = SaleResultVM.class,
                  authorizations = {@Authorization(value = "basic_auth")},
                  tags={ "payment" })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully processed", response = SaleResultVM.class),
        @ApiResponse(code = 400, message = "Invalid input data supplied", response = SaleResultVM.class),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid", response = SaleResultVM.class),
        @ApiResponse(code = 403, message = "User isn't allow to perform the requested action", response = SaleResultVM.class),
        @ApiResponse(code = 500, message = "Sorry we can't process this request at the moment", response = SaleResultVM.class),
        @ApiResponse(code = 502, message = "Invalid response received from third party server", response = SaleResultVM.class)})
    @PostMapping(value = "/v1/unified/payments/sale",
        produces = { "application/json" },
        consumes = { "application/json" })
    public ResponseEntity<SaleResultVM> submitSale(@ApiParam(value = "Merchant reference", required=true)
                                                        @RequestHeader(value="X-Unified-Payments-Merchant-Reference")
                                                        String merchantReference,
                                                   @ApiParam(value = "Details of the Sale payment", required=true )
                                                        @Valid @RequestBody SaleRequestVM request) {
        Optional<ResponseEntity<SaleResultVM>> validationError = validateInput(merchantReference, request);
        if (validationError.isPresent()) return validationError.get();

        Pair<AvailableProcessor, SaleRequest> requestPair = buildSaleRequest(merchantReference, request);
        SaleResult saleResult = paymentGateway.using(requestPair.getFirst()).sale(requestPair.getSecond());
        SaleResultVM result = SaleResultVM.fromGatewayResult(saleResult);

        TransactionDTO transaction = transactionMapper.toDTO(request, result);
        transactionService.save(fillIds(merchantReference, request, transaction));
        if (result.isOK()) return ResponseEntity.ok().body(result);
        return new ResponseEntity<>(result, HttpStatus.BAD_GATEWAY);
    }

    private Optional<ResponseEntity<SaleResultVM>> validateInput(String merchantReference, SaleRequestVM request) {
        Optional<ResponseEntity<SaleResultVM>> unauthorized = validateMerchant(merchantReference, request);
        if (unauthorized.isPresent()) return unauthorized;

        Optional<ResponseEntity<SaleResultVM>> badRequest = validateToken(merchantReference, request);
        if (badRequest.isPresent()) return badRequest;

        badRequest = validateUserMerchant(merchantReference);
        if (badRequest.isPresent()) return badRequest;

        return validateCurrency(request);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Pair<AvailableProcessor, SaleRequest> buildSaleRequest(String merchantReference, SaleRequestVM request) {
        Optional<MerchantDTO> merchantDTO = merchantService.findOneByReference(merchantReference);
        long merchantId = merchantDTO.isPresent() ? merchantDTO.get().getId() : -1;
        Optional<AvailableProcessor> processor = AvailableProcessor.fromReference(request.getPaymentProcessor());
        long paymentMethodId = processor.isPresent() ? processor.get().getPaymentMethodId() : -1;
        Optional<PaymentMethodCredentialDTO> credential = credentialService.findOneByPaymentMethodAndMerchant(
                                                                                paymentMethodId, merchantId);
        String credentialJson = "";

        if (credential.isPresent()) {
           //TODO probably we want to use a different key for the credentials than for the cards
           credentialJson = strongCryptography.decrypt(credential.get().getCredentials());
        }
        String reference = referenceGenerator.generate();
        SaleRequest saleRequest = request.toSaleRequest(reference)
                                         .merchantId(merchantId)
                                         .merchantCredentialsJson(credentialJson)
                                         .currencyIsoCode(request.getCurrencyIsoCode());
        return Pair.of(processor.get(), saleRequest);
    }

    private TransactionDTO fillIds(String merchantReference, SaleRequestVM request, TransactionDTO transaction) {
        Optional<MerchantDTO> merchantDTO = merchantService.findOneByReference(merchantReference);
        Long merchantId = merchantDTO.isPresent() ? merchantDTO.get().getId() : -1L;
        Optional<AvailableProcessor> processor = AvailableProcessor.fromReference(request.getPaymentProcessor());
        Long paymentMethodId = processor.isPresent() ? processor.get().getPaymentMethodId() : -1;
        Optional<UnifiedPaymentTokenDTO> tokenDTO = tokenService.findOneByTokenAndMerchantId(request.getToken(), merchantId);
        Long tokenId = tokenDTO.isPresent() ? tokenDTO.get().getId() : -1L;
        Optional<CurrencyDTO> currencyDTO = currencyService.findOneByIsoCode(request.getCurrencyIsoCode());
        Long currencyId = currencyDTO.isPresent() ? currencyDTO.get().getId() : -1L;

        transaction.setMerchantId(merchantId);
        transaction.setCurrencyId(currencyId);
        transaction.setUnifiedPaymentTokenId(tokenId);
        transaction.setPaymentMethodId(paymentMethodId);
        return transaction;
    }

    private Optional<ResponseEntity<SaleResultVM>> validateMerchant(String merchantReference, SaleRequestVM request) {
        Optional<MerchantDTO> merchantDTO = merchantService.findOneByReference(merchantReference);
        if (merchantDTO.isEmpty()) {
            SaleResultVM result = new SaleResultVM().resultCode(SaleResultVM.ResultCodeEnum.VALIDATION_ERROR)
                                                    .resultDescription("Authentication information is missing or invalid");
            return Optional.of(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result));
        }

        Optional<AvailableProcessor> processor = AvailableProcessor.fromReference(request.getPaymentProcessor());
        if (processor.isEmpty()) {
            SaleResultVM result = new SaleResultVM().resultCode(SaleResultVM.ResultCodeEnum.VALIDATION_ERROR)
                                                    .resultDescription("Invalid payment processor supplied");
            return Optional.of(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result));
        }

        Long merchantId = merchantDTO.get().getId();
        Long paymentMethodId = processor.get().getPaymentMethodId();
        Optional<PaymentMethodCredentialDTO> credentialDTO = credentialService.findOneByPaymentMethodAndMerchant(paymentMethodId, merchantId);

        if (credentialDTO.isEmpty()) {
            SaleResultVM result = new SaleResultVM().resultCode(SaleResultVM.ResultCodeEnum.VALIDATION_ERROR)
                                                    .resultDescription("Third party credentials are incorrect for required payment method");
            return Optional.of(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result));
        }
        return Optional.empty();
    }

    private Optional<ResponseEntity<SaleResultVM>> validateUserMerchant(String merchantReference) {
        if (merchantService.isNotAuthorizedUserOwnedByMerchant(merchantReference)) {
            SaleResultVM result = new SaleResultVM().resultCode(SaleResultVM.ResultCodeEnum.VALIDATION_ERROR)
                                                    .resultDescription("User doesn't have enough credentials to access resource");
            return Optional.of(ResponseEntity.status(HttpStatus.FORBIDDEN).body(result));
        }
        return Optional.empty();
    }

    private Optional<ResponseEntity<SaleResultVM>> validateToken(String merchantReference, SaleRequestVM request) {
        Optional<MerchantDTO> merchantDTO = merchantService.findOneByReference(merchantReference);
        Long merchantId = merchantDTO.isPresent() ? merchantDTO.get().getId() : -1L;

        Optional<UnifiedPaymentTokenDTO> tokenDTO = tokenService.findOneByTokenAndMerchantId(request.getToken(), merchantId);
        if (tokenDTO.isEmpty()) {
            SaleResultVM result = new SaleResultVM().resultCode(SaleResultVM.ResultCodeEnum.VALIDATION_ERROR)
                                                    .resultDescription("Invalid token supplied");
            return Optional.of(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result));
        }

        return Optional.empty();
    }

    private Optional<ResponseEntity<SaleResultVM>> validateCurrency(SaleRequestVM request) {
        Optional<CurrencyDTO> currencyDTO = currencyService.findOneByIsoCode(request.getCurrencyIsoCode());
        if (currencyDTO.isEmpty()) {
            SaleResultVM result = new SaleResultVM().resultCode(SaleResultVM.ResultCodeEnum.VALIDATION_ERROR)
                                                    .resultDescription("Invalid currency code supplied");
            return Optional.of(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result));
        }
        return Optional.empty();
    }
}
