package com.fun.driven.development.fun.unified.payments.gateway.core;

import javax.annotation.Nonnull;

public enum AvailableProcessor {
    BRAINTREE("braintree");

    private String reference;

    AvailableProcessor(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
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
