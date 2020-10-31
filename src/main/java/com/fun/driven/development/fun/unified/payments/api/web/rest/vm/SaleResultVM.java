package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleResult;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleResultVM {

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

    @JsonIgnore
    private String processorResult;

    public static SaleResultVM fromGatewayResult(SaleResult saleResult) {
        ResultCodeEnum resultCodeEnum = ResultCodeEnum.valueOf(saleResult.getResultCode().name());
        return new SaleResultVM().resultCode(resultCodeEnum)
                                 .resultDescription(saleResult.getResultDescription())
                                 .reference(saleResult.getReference())
                                 .processorResult(saleResult.getProcessorResult());
    }

    public SaleResultVM resultCode(ResultCodeEnum resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public ResultCodeEnum getResultCode() {
        return resultCode;
    }

    public SaleResultVM resultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
        return this;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public String getReference() {
        return reference;
    }

    public SaleResultVM reference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getProcessorResult() {
        return processorResult;
    }

    public SaleResultVM processorResult(String processorResult) {
        this.processorResult = processorResult;
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
        SaleResultVM paymentResult = (SaleResultVM) o;
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

