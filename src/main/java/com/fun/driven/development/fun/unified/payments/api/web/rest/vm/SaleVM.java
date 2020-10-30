package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SaleVM {

    @JsonProperty
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Size(max = 500)
    private String token;

    @JsonProperty
    @ApiModelProperty(example = "100", required = true, value = "")
    @NotNull
    @Min(1L)
    @Max(100000000L)
    private Long amountInCents;

    @JsonProperty
    @ApiModelProperty(example = "EUR", required = true, value = "EUR")
    @Size(min = 3, max = 3)
    private String currencyIsoCode;

    public SaleVM() {
        currencyIsoCode = "EUR";
    }

    public SaleVM token(String token) {
        this.token = token;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SaleVM amountInCents(Long amountInCents) {
        this.amountInCents = amountInCents;
        return this;
    }

    public Long getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(Long amountInCents) {
        this.amountInCents = amountInCents;
    }

    public SaleVM currencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
        return this;
    }

    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public void setCurrencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SaleVM sale = (SaleVM) o;
        return Objects.equals(this.token, sale.token) &&
            Objects.equals(this.amountInCents, sale.amountInCents) &&
            Objects.equals(this.currencyIsoCode, sale.currencyIsoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, amountInCents, currencyIsoCode);
    }

    @Override
    public String toString() {
        return "SaleVM{" + "token='" + token + '\'' +
            ", amountInCents=" + amountInCents +
            ", currencyIsoCode='" + currencyIsoCode + '\'' + '}';
    }
}

