package com.fun.driven.development.fun.unified.payments.api.web.rest;

import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.CardVM;
import io.github.jhipster.web.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Api(value = "token")
@RequestMapping("/api")
public class TokenResource {

    private static final String ENTITY_NAME = "token";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    /**
     * POST /v1/unified/tokens : Generate a unified payment token for a card
     *
     * @param merchantReference  (required)
     * @param card Card for which the token will be generated (required)
     * @return Successful operation (status code 200)
     *         or Invalid card supplied (status code 400)
     *         or Authentication information is missing or invalid (status code 401)
     *         or User isn&#39;t allow to perform the requested action (status code 403)
     */
    @ApiOperation(value = "Generate a unified payment token for a card", nickname = "createToken",
                  notes = "", response = UnifiedPaymentTokenDTO.class,
                  authorizations = {@Authorization(value = "basic_auth")},
                  tags={ "token" })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created", response = UnifiedPaymentTokenDTO.class),
        @ApiResponse(code = 400, message = "Invalid card supplied"),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid"),
        @ApiResponse(code = 403, message = "User isn't allow to perform the requested action"),
        @ApiResponse(code = 500, message = "Sorry we can't process this request at the moment")})
    @PostMapping(value = "/v1/unified/tokens",
        produces = { "application/json" },
        consumes = { "application/json" })
    public ResponseEntity<UnifiedPaymentTokenDTO> createToken(@ApiParam(value = "Merchant reference", required=true)
                                                                  @RequestHeader(value="X-Unified-Payments-Merchant-Reference") String merchantReference,
                                                              @ApiParam(value = "Card for which the token will be generated", required=true, name = "Card")
                                                                  @Valid @RequestBody CardVM card)
                                                              throws URISyntaxException {
        UnifiedPaymentTokenDTO result = new UnifiedPaymentTokenDTO();
        result.setId(1L);
        return ResponseEntity.created(new URI("/api/v1/unified/tokens/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }
}
