package com.fun.driven.development.fun.unified.payments.api.web.rest;

import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.TransactionType;
import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.UnifiedTransactionResult;
import com.fun.driven.development.fun.unified.payments.api.service.CurrencyService;
import com.fun.driven.development.fun.unified.payments.api.service.MerchantService;
import com.fun.driven.development.fun.unified.payments.api.service.PaymentMethodCredentialService;
import com.fun.driven.development.fun.unified.payments.api.service.TransactionService;
import com.fun.driven.development.fun.unified.payments.api.service.UnifiedPaymentTokenService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.CurrencyDTO;
import com.fun.driven.development.fun.unified.payments.api.service.dto.MerchantDTO;
import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodCredentialDTO;
import com.fun.driven.development.fun.unified.payments.api.service.dto.TransactionDTO;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleRequestVM;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleResultVM;
import com.fun.driven.development.fun.unified.payments.gateway.core.AvailableProcessor;
import com.fun.driven.development.fun.unified.payments.gateway.core.PaymentGateway;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleRequest;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleResult;
import com.fun.driven.development.fun.unified.payments.gateway.util.ReferenceGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;
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
    private UnifiedPaymentTokenService tokenService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private ReferenceGenerator referenceGenerator;

    @Autowired
    private TransactionService transactionService;

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

        TransactionDTO transaction = vmToDTO(request, result);
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
        Long merchantId = merchantDTO.isPresent() ? merchantDTO.get().getId() : -1;
        Optional<AvailableProcessor> processor = AvailableProcessor.fromReference(request.getPaymentProcessor());
        Long paymentMethodId = processor.isPresent() ? processor.get().getPaymentMethodId() : -1;
        Optional<PaymentMethodCredentialDTO> credential = credentialService.findOneByPaymentMethodAndMerchant(
                                                                                paymentMethodId, merchantId);
        String credentialJson = credential.isPresent() ? credential.get().getCredentials() : "";
        String reference = referenceGenerator.generate();
        SaleRequest saleRequest = request.toSaleRequest(reference)
                                         .merchantCredentialsJson(credentialJson)
                                         .currencyIsoCode(request.getCurrencyIsoCode());
        return Pair.of(processor.get(), saleRequest);
    }

    public TransactionDTO vmToDTO(SaleRequestVM request, SaleResultVM result) {
        TransactionDTO tx = new TransactionDTO();
        tx.setAmount(request.getAmountInCents());
        tx.setFunReference(result.getReference());
        tx.setTransactionType(TransactionType.SALE);
        tx.setTransactionDate(Instant.now());
        tx.setResult(UnifiedTransactionResult.valueOf(result.getResultCode().name()));
        tx.setProcessorResult(result.getProcessorResult());
        tx.setExternalReference(request.getExternalReference());
        return tx;
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
