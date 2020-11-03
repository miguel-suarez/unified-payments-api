package com.fun.driven.development.fun.unified.payments.api.web.rest;

import com.fun.driven.development.fun.unified.payments.api.service.MerchantService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.MerchantDTO;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.CardVM;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.TokenVM;
import com.fun.driven.development.fun.unified.payments.vault.Card;
import com.fun.driven.development.fun.unified.payments.vault.PaymentDetailsVault;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Api(value = "token")
@RequestMapping("/api")
public class TokenResource {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private PaymentDetailsVault paymentDetailsVault;

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
                  response = TokenVM.class,
                  authorizations = {@Authorization(value = "basic_auth")},
                  tags={ "token" })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully created", response = TokenVM.class),
        @ApiResponse(code = 400, message = "Invalid card supplied", response = TokenVM.class),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid", response = TokenVM.class),
        @ApiResponse(code = 403, message = "User isn't allow to perform the requested action", response = TokenVM.class),
        @ApiResponse(code = 500, message = "Sorry we can't process this request at the moment", response = TokenVM.class)})
    @PostMapping(value = "/v1/unified/tokens",
        produces = { "application/json" },
        consumes = { "application/json" })
    public ResponseEntity<TokenVM> createToken(@ApiParam(value = "Merchant reference", required=true)
                                                   @RequestHeader(value="X-Unified-Payments-Merchant-Reference") String merchantReference,
                                               @ApiParam(value = "Card for which the token will be generated", required=true, name = "Card")
                                                   @Valid @RequestBody CardVM card) {
        Optional<ResponseEntity<TokenVM>> validationError = validateInput(merchantReference, card);
        if (validationError.isPresent()) return validationError.get();

        long merchantId = findMerchantId(merchantReference);
        UnifiedPaymentTokenDTO tokenDTO = paymentDetailsVault.tokenize(merchantId, vmToDTO(card));
        return ResponseEntity.ok().body(TokenVM.from(tokenDTO));
    }

    private Card vmToDTO(CardVM cardVM) {
        return new Card().setNumber(cardVM.getCardNumber())
                         .setExpirationMonth(cardVM.getExpiryMonth())
                         .setExpirationYear(cardVM.getExpiryYear());
    }

    private long findMerchantId(String merchantReference) {
        Optional<MerchantDTO> merchantDTO = merchantService.findOneByReference(merchantReference);
        return merchantDTO.isPresent() ? merchantDTO.get().getId() : -1;
    }

    private Optional<ResponseEntity<TokenVM>> validateInput(String merchantReference, CardVM card) {
        Optional<ResponseEntity<TokenVM>> unauthorized = validateMerchant(merchantReference);
        if (unauthorized.isPresent()) return unauthorized;

        Optional<ResponseEntity<TokenVM>> badRequest = validateUserMerchant(merchantReference);
        if (badRequest.isPresent()) return badRequest;

        return validateCard(card);
    }

    private Optional<ResponseEntity<TokenVM>> validateMerchant(String merchantReference) {
        Optional<MerchantDTO> merchantDTO = merchantService.findOneByReference(merchantReference);
        if (merchantDTO.isEmpty()) {
            TokenVM result = new TokenVM().errorMessage("Authentication information is missing or invalid");
            return Optional.of(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result));
        }
        return Optional.empty();
    }

    private Optional<ResponseEntity<TokenVM>> validateUserMerchant(String merchantReference) {
        if (merchantService.isNotAuthorizedUserOwnedByMerchant(merchantReference)) {
            TokenVM result = new TokenVM().errorMessage("User doesn't have enough credentials to access resource");
            return Optional.of(ResponseEntity.status(HttpStatus.FORBIDDEN).body(result));
        }
        return Optional.empty();
    }

    private Optional<ResponseEntity<TokenVM>> validateCard(CardVM card) {
        if (card.hasInvalidCardNumber()) {
            TokenVM result = new TokenVM().errorMessage("Invalid card number supplied");
            return Optional.of(ResponseEntity.badRequest().body(result));
        }
        if (card.isExpired()) {
            TokenVM result = new TokenVM().errorMessage("Expired card supplied");
            return Optional.of(ResponseEntity.badRequest().body(result));
        }
        return Optional.empty();
    }

}
