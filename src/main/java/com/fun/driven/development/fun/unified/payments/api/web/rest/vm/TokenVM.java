package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;

@ApiModel(description = "Fun unified payment token")
@JsonRootName(value = "Token")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenVM {

    @ApiModelProperty
    @Size(max = 500)
    private String token;

    @ApiModelProperty(example = "2020-10-16T15:01:54Z")
    @Pattern(regexp="/^\\d{4}-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d(\\.\\d+)?(([+-]\\d\\d:\\d\\d)|Z)?$/i")
    private Instant validUntil;

    @ApiModelProperty(example = "User isn't allow to perform the requested action")
    private String errorMessage;

    public static TokenVM from(UnifiedPaymentTokenDTO token) {
        return new TokenVM().token(token.getToken())
                            .validUntil(token.getValidUntil());
    }

    public String getToken() {
        return token;
    }

    public TokenVM token(String token) {
        this.token = token;
        return this;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public TokenVM validUntil(Instant validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public TokenVM errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
