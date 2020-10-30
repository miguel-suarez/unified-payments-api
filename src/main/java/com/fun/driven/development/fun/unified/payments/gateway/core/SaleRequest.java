package com.fun.driven.development.fun.unified.payments.gateway.core;

import com.fun.driven.development.fun.unified.payments.vault.UnifiedToken;

public class SaleRequest {

    private String reference;

    private UnifiedToken token;

    private Long amountInCents;

    private String currencyIsoCode;

    private String merchantCredentialsJson;

    public String getReference() {
        return reference;
    }

    public SaleRequest reference(String reference) {
        this.reference = reference;
        return this;
    }

    public UnifiedToken getToken() {
        return token;
    }

    public SaleRequest token(UnifiedToken token) {
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
}
