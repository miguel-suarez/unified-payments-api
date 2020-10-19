package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "Card for which the token will be generated")
@JsonRootName(value = "Card")
public class CardVM {

    @JsonProperty("cardNumber")
    @ApiModelProperty(example = "370000000000002", required = true)
    @Size(min=10,max=20)
    @NotNull
    private String cardNumber;

    @JsonProperty("expiryMonth")
    @ApiModelProperty(example = "3", required = true)
    @NotNull
    @Min(1) @Max(12)
    private Integer expiryMonth;

    @JsonProperty("expiryYear")
    @ApiModelProperty(example = "2030", required = true)
    @NotNull
    @Min(2020) @Max(2100)
    private Integer expiryYear;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }
}
