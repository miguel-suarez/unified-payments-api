package com.fun.driven.development.fun.unified.payments.api.web.rest;

import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.PaymentResultVM;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleVM;
import io.github.jhipster.web.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Api(value = "token")
@RequestMapping("/api")
public class PaymentResource {

    private static final String ENTITY_NAME = "payment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    /**
     * POST /v1/unified/payments/sale : Submits a sale payment request
     *
     * @param merchantReference  (required)
     * @param sale Details of the sale payment (required)
     * @return Successfully created (status code 201)
     *         or Invalid input data supplied (status code 400)
     *         or Authentication information is missing or invalid (status code 401)
     *         or User isn&#39;t allow to perform the requested action (status code 403)
     *         or Sorry we can&#39;t process this request at the moment (status code 500)
     */
    @ApiOperation(value = "Submits a sale payment request", nickname = "submitSale",
                  notes = "", response = PaymentResultVM.class,
                  authorizations = {@Authorization(value = "basic_auth")},
                  tags={ "payment" })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created", response = PaymentResultVM.class),
        @ApiResponse(code = 400, message = "Invalid input data supplied"),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid"),
        @ApiResponse(code = 403, message = "User isn't allow to perform the requested action"),
        @ApiResponse(code = 500, message = "Sorry we can't process this request at the moment") })
    @RequestMapping(value = "/v1/unified/payments/sale",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    public ResponseEntity<PaymentResultVM> submitSale(@ApiParam(required=true)
                                                        @RequestHeader(value="X-Unified-Payments-Merchant-Reference") String merchantReference,
                                                      @ApiParam(value = "Details of the sale payment", required=true )
                                                        @Valid @RequestBody SaleVM sale)
                                                throws URISyntaxException {
        PaymentResultVM result = new PaymentResultVM();
        result.setResultCode(PaymentResultVM.ResultCodeEnum.SUCCESS);
        return ResponseEntity.created(new URI("/api/v1/unified/payments/sale/" + result.getResultCode()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getResultCode().toString()))
                             .body(result);
    }
}
