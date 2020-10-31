package com.fun.driven.development.fun.unified.payments.gateway.core;

public class SaleResult {

    public enum ResultCode {
        AUTHORIZED,
        CANCELLED,
        ERROR,
        PENDING,
        REFUSED,
        SUCCESS,
        VALIDATION_ERROR
    }

    private String reference;

    private ResultCode resultCode;

    private String processorResult;

    private String resultDescription;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public SaleResult reference(String reference) {
        this.reference = reference;
        return this;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public SaleResult resultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public String getProcessorResult() {
        return processorResult;
    }

    public SaleResult processorResult(String processorResult) {
        this.processorResult = processorResult;
        return this;
    }

    public SaleResult resultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
        return this;
    }

    @Override
    public String toString() {
        return "SaleResult{" +
            "reference='" +
            reference +
            '\'' +
            ", resultCode=" +
            resultCode +
            ", resultDescription='" +
            resultDescription +
            '\'' +
            '}';
    }
}
