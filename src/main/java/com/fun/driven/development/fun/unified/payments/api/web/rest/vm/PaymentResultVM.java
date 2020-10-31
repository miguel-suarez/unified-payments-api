package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleResult;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResultVM {

    public enum ResultCodeEnum {
        AUTHORIZED,
        CANCELLED,
        ERROR,
        PENDING,
        REFUSED,
        SUCCESS,
        VALIDATION_ERROR
    }

    @JsonProperty
    @ApiModelProperty(example = "AUTHORIZED")
    private ResultCodeEnum resultCode;

    @JsonProperty
    @ApiModelProperty(example = "Sale authorized with code b325yb0j.")
    private String resultDescription;

    @JsonProperty()
    @ApiModelProperty(example = "19999160406806200009")
    private String reference;

    public static PaymentResultVM fromSaleResult(SaleResult saleResult) {
        ResultCodeEnum resultCodeEnum = ResultCodeEnum.valueOf(saleResult.getResultCode().name());
        return new PaymentResultVM().resultCode(resultCodeEnum)
                                    .resultDescription(saleResult.getResultDescription())
                                    .reference(saleResult.getReference());
    }

    public PaymentResultVM resultCode(ResultCodeEnum resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public ResultCodeEnum getResultCode() {
        return resultCode;
    }

    public PaymentResultVM resultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
        return this;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public String getReference() {
        return reference;
    }

    public PaymentResultVM reference(String reference) {
        this.reference = reference;
        return this;
    }

    @JsonIgnore
    public boolean isOK() {
        return resultCode.equals(ResultCodeEnum.AUTHORIZED) ||
               resultCode.equals(ResultCodeEnum.SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentResultVM paymentResult = (PaymentResultVM) o;
        return Objects.equals(this.resultCode, paymentResult.resultCode) && Objects.equals(this.resultDescription, paymentResult.resultDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultCode, resultDescription);
    }

    @Override
    public String toString() {
        return "PaymentResultVM{" + "resultCode=" + resultCode +
            ", resultDescription='" + resultDescription + '\'' + '}';
    }
}

