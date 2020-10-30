package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class PaymentResultVM {

    @JsonProperty
    @ApiModelProperty(example = "Success", value = "")
    private ResultCodeEnum resultCode;

    @JsonProperty
    @ApiModelProperty(example = "Payment successful", value = "")
    private String resultDescription;

    public PaymentResultVM resultCode(ResultCodeEnum resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public ResultCodeEnum getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCodeEnum resultCode) {
        this.resultCode = resultCode;
    }

    public PaymentResultVM resultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
        return this;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
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

