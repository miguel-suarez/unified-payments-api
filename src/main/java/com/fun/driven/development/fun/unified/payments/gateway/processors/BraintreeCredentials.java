package com.fun.driven.development.fun.unified.payments.gateway.processors;

import org.springframework.util.StringUtils;

public class BraintreeCredentials {
    private String merchantId;
    private String publicKey;
    private String privateKey;

    public String getMerchantId() {
        return merchantId;
    }

    public BraintreeCredentials merchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public BraintreeCredentials publicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public BraintreeCredentials privateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public boolean areEmpty() {
        return StringUtils.isEmpty(merchantId) ||
               StringUtils.isEmpty(publicKey) ||
               StringUtils.isEmpty(privateKey);
    }
}
