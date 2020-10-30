package com.fun.driven.development.fun.unified.payments.gateway.core;

import javax.annotation.Nonnull;

public enum AvailableProcessor {
    BRAINTREE("braintree", 1L);

    private String reference;
    private Long paymentMethodId;

    AvailableProcessor(String reference, Long paymentMethodId) {
        this.reference = reference;
        this.paymentMethodId = paymentMethodId;
    }

    public String getReference() {
        return reference;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public static AvailableProcessor fromReference(@Nonnull String reference) {
        for (AvailableProcessor processor : AvailableProcessor.values()) {
            if (processor.reference.equals(reference)) {
                return processor;
            }
        }
        throw new IllegalArgumentException("Unexpected reference '" + reference + "'");
    }

}
