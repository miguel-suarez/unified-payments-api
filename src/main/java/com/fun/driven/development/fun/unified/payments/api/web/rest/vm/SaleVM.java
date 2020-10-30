package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fun.driven.development.fun.unified.payments.gateway.core.AvailableProcessor;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleRequest;
import com.fun.driven.development.fun.unified.payments.vault.UnifiedToken;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SaleVM {

    @JsonProperty
    @ApiModelProperty(required = true)
    @NotNull
    @Size(max = 500)
    private String token;

    @JsonProperty
    @ApiModelProperty(example = "100", required = true)
    @NotNull
    @Min(1L)
    @Max(100000000L)
    private Long amountInCents;

    @JsonProperty
    @ApiModelProperty(example = "EUR", value = "EUR")
    @Size(min = 3, max = 3)
    private String currencyIsoCode;

    @JsonProperty
    @ApiModelProperty(example = "braintree", value = "braintree")
    @Size(max = 50)
    private String paymentProcessor;

    public SaleVM() {
        // Default values for optional parameters
        currencyIsoCode = "EUR";
        paymentProcessor = AvailableProcessor.BRAINTREE.getReference();
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

    public String getPaymentProcessor() {
        return paymentProcessor;
    }

    public void setPaymentProcessor(String paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public SaleVM paymentProcessor(String paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
        return this;
    }

    // Mapstruct is not used this mapping doesn't fit the entity / DTO interface defined
    public SaleRequest toSaleRequest(String reference) {
        return new SaleRequest().amountInCents(amountInCents)
                                .currencyIsoCode(currencyIsoCode)
                                .token(new UnifiedToken(token))
                                .reference(reference);
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
            Objects.equals(this.currencyIsoCode, sale.currencyIsoCode) &&
            Objects.equals(this.paymentProcessor, sale.paymentProcessor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, amountInCents, currencyIsoCode, paymentProcessor);
    }

    @Override
    public String toString() {
        return "SaleVM{" +
            "token='" + token + '\'' +
            ", amountInCents=" + amountInCents +
            ", currencyIsoCode='" + currencyIsoCode + '\'' +
            ", paymentProcessor='" + paymentProcessor + '\'' +
            '}';
    }
}

