package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleResult;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class PaymentResultVM {

    @JsonProperty
    @ApiModelProperty(example = "AUTHORIZED")
    private ResultCodeEnum resultCode;

    @JsonProperty
    @ApiModelProperty(example = "Sale authorized with code b325yb0j.")
    private String resultDescription;

    @JsonProperty
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

    public enum ResultCodeEnum {
        AUTHORIZED("Authorized"),
        CANCELLED("Cancelled"),
        ERROR("Error"),
        PENDING("Pending"),
        REFUSED("Refused"),
        SUCCESS("Success"),
        VALIDATION_ERROR("ValidationError");

        private String value;

        ResultCodeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static ResultCodeEnum fromValue(String value) {
            for (ResultCodeEnum b : ResultCodeEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}

