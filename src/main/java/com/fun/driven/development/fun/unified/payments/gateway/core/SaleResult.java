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

    private String resultDescription;

    public String getReference() {
        return reference;
    }

    public SaleResult setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public SaleResult setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public SaleResult setResultDescription(String resultDescription) {
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
