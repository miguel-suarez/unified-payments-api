package com.fun.driven.development.fun.unified.payments.gateway.core;

public class SaleRequest {

    private String reference;

    private String token;

    private Long amountInCents;

    private String currencyIsoCode;

    private long merchantId;

    private String merchantCredentialsJson;

    public String getReference() {
        return reference;
    }

    public SaleRequest reference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getToken() {
        return token;
    }

    public SaleRequest token(String token) {
        this.token = token;
        return this;
    }

    public Long getAmountInCents() {
        return amountInCents;
    }

    public SaleRequest amountInCents(Long amountInCents) {
        this.amountInCents = amountInCents;
        return this;
    }

    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public SaleRequest currencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
        return this;
    }

    public String getMerchantCredentialsJson() {
        return merchantCredentialsJson;
    }

    public SaleRequest merchantCredentialsJson(String merchantCredentialsJson) {
        this.merchantCredentialsJson = merchantCredentialsJson;
        return this;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public SaleRequest merchantId(long merchantId) {
        this.merchantId = merchantId;
        return this;
    }
}
