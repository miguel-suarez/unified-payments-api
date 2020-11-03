package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fun.driven.development.fun.unified.payments.gateway.core.AvailableProcessor;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@ApiModel(description = "Details of the Sale to be processed")
@JsonRootName(value = "SaleRequest")
public class SaleRequestVM {

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

    @JsonProperty
    @ApiModelProperty(example = "merchant12345")
    @Size(max = 25)
    private String externalReference;

    public SaleRequestVM() {
        // Default values for optional parameters
        currencyIsoCode = "EUR";
        paymentProcessor = AvailableProcessor.BRAINTREE.getReference();
    }

    public SaleRequestVM token(String token) {
        this.token = token;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SaleRequestVM amountInCents(Long amountInCents) {
        this.amountInCents = amountInCents;
        return this;
    }

    public Long getAmountInCents() {
        return amountInCents;
    }

    public SaleRequestVM currencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
        return this;
    }

    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public String getPaymentProcessor() {
        return paymentProcessor;
    }

    public SaleRequestVM paymentProcessor(String paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
        return this;
    }

    public String getExternalReference() {
        return externalReference;
    }

    // Mapstruct is not used this mapping doesn't fit the entity / DTO interface defined
    public SaleRequest toSaleRequest(String reference) {
        return new SaleRequest().amountInCents(amountInCents)
                                .currencyIsoCode(currencyIsoCode)
                                .token(token)
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
        SaleRequestVM sale = (SaleRequestVM) o;
        return Objects.equals(this.token, sale.token) &&
            Objects.equals(this.amountInCents, sale.amountInCents) &&
            Objects.equals(this.currencyIsoCode, sale.currencyIsoCode) &&
            Objects.equals(this.paymentProcessor, sale.paymentProcessor) &&
            Objects.equals(this.externalReference, sale.externalReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, amountInCents, currencyIsoCode,
                            paymentProcessor, externalReference);
    }

    @Override
    public String toString() {
        return "SaleVM{" +
            "token='" + token + '\'' +
            ", amountInCents=" + amountInCents +
            ", currencyIsoCode='" + currencyIsoCode + '\'' +
            ", paymentProcessor='" + paymentProcessor + '\'' +
            ", externalReference='" + externalReference + '\'' +
            '}';
    }
}

