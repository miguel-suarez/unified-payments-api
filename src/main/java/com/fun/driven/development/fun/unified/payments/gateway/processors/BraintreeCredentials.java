package com.fun.driven.development.fun.unified.payments.gateway.processors;

public class BraintreeCredentials {
    private String merchantId;
    private String publicKey;
    private String privateKey;

    public String getMerchantId() {
        return merchantId;
    }

    public BraintreeCredentials setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public BraintreeCredentials setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public BraintreeCredentials setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }
}
