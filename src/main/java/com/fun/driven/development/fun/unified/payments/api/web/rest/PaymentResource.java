package com.fun.driven.development.fun.unified.payments.api.web.rest;

import com.fun.driven.development.fun.unified.payments.api.service.CurrencyService;
import com.fun.driven.development.fun.unified.payments.api.service.MerchantService;
import com.fun.driven.development.fun.unified.payments.api.service.PaymentMethodCredentialService;
import com.fun.driven.development.fun.unified.payments.api.service.UnifiedPaymentTokenService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.CurrencyDTO;
import com.fun.driven.development.fun.unified.payments.api.service.dto.MerchantDTO;
import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodCredentialDTO;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.PaymentResultVM;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleVM;
import com.fun.driven.development.fun.unified.payments.gateway.core.AvailableProcessor;
import com.fun.driven.development.fun.unified.payments.gateway.core.PaymentGateway;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleRequest;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleResult;
import com.fun.driven.development.fun.unified.payments.gateway.util.ReferenceGenerator;
import io.github.jhipster.web.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@Api(value = "token")
@RequestMapping("/api")
public class PaymentResource {

    private static final String ENTITY_NAME = "payment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

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
                  response = PaymentResultVM.class,
                  authorizations = {@Authorization(value = "basic_auth")},
                  tags={ "payment" })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created", response = PaymentResultVM.class),
        @ApiResponse(code = 400, message = "Invalid input data supplied"),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid"),
        @ApiResponse(code = 403, message = "User isn't allow to perform the requested action"),
        @ApiResponse(code = 500, message = "Sorry we can't process this request at the moment") })
    @PostMapping(value = "/v1/unified/payments/sale",
        produces = { "application/json" },
        consumes = { "application/json" })
    public ResponseEntity<PaymentResultVM> submitSale(@ApiParam(required=true)
                                                        @RequestHeader(value="X-Unified-Payments-Merchant-Reference") String merchantReference,
                                                      @ApiParam(value = "Details of the sale payment", required=true )
                                                        @Valid @RequestBody SaleVM request)
                                                throws URISyntaxException {
        Optional<ResponseEntity<PaymentResultVM>> validationError = validateInput(merchantReference, request);
        //TODO validate credentials and processor
        if (validationError.isPresent()) return validationError.get();

        Optional<MerchantDTO> merchantDTO = merchantService.findOneByReference(merchantReference);
        AvailableProcessor processor = AvailableProcessor.fromReference(request.getPaymentProcessor());
        Optional<PaymentMethodCredentialDTO> credential = credentialService.findOneByPaymentMethodAndMerchant(merchantDTO.get().getId(),
                                                                                                              processor.getPaymentMethodId());
        String reference = referenceGenerator.generate();
        SaleRequest saleRequest = request.toSaleRequest(reference)
                                         .merchantCredentialsJson(credential.get().getCredentials());
        SaleResult saleResult = paymentGateway.using(processor).sale(saleRequest);
        PaymentResultVM result = PaymentResultVM.fromSaleResult(saleResult);

        return ResponseEntity.created(new URI("/api/v1/unified/payments/sale/" + saleResult.getResultCode()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, saleResult.getResultCode().toString()))
                             .body(result);
    }

    private Optional<ResponseEntity<PaymentResultVM>> validateInput(String merchantReference, SaleVM sale) {
        Optional<ResponseEntity<PaymentResultVM>> unauthorized = validateMerchantReference(merchantReference);
        if (unauthorized.isPresent()) return unauthorized;

        Optional<ResponseEntity<PaymentResultVM>> badRequest = validateToken(sale);
        if (badRequest.isPresent()) return badRequest;

        return validateCurrency(sale);
    }

    private Optional<ResponseEntity<PaymentResultVM>> validateMerchantReference(String merchantReference) {
        Optional<MerchantDTO> merchantDTO = merchantService.findOneByReference(merchantReference);
        if (merchantDTO.isEmpty()) {
            PaymentResultVM result = new PaymentResultVM().resultCode(PaymentResultVM.ResultCodeEnum.VALIDATION_ERROR)
                                                          .resultDescription("Authentication information is missing or invalid");
            return Optional.of(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result));
        }
        return Optional.empty();
    }

    private Optional<ResponseEntity<PaymentResultVM>> validateToken(SaleVM sale) {
        Optional<UnifiedPaymentTokenDTO> tokenDTO = tokenService.findOneByToken(sale.getToken());
        if (tokenDTO.isEmpty()) {
            PaymentResultVM result = new PaymentResultVM().resultCode(PaymentResultVM.ResultCodeEnum.VALIDATION_ERROR)
                                                          .resultDescription("Invalid token supplied");
            return Optional.of(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result));
        }
        return Optional.empty();
    }

    private Optional<ResponseEntity<PaymentResultVM>> validateCurrency(SaleVM sale) {
        Optional<CurrencyDTO> currencyDTO = currencyService.findOneByIsoCode(sale.getCurrencyIsoCode());
        if (currencyDTO.isEmpty()) {
            PaymentResultVM result = new PaymentResultVM().resultCode(PaymentResultVM.ResultCodeEnum.VALIDATION_ERROR)
                                                          .resultDescription("Invalid currency code supplied");
            return Optional.of(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result));
        }
        return Optional.empty();
    }
}
