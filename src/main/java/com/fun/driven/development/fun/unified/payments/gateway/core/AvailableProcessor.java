package com.fun.driven.development.fun.unified.payments.gateway.core;

import javax.annotation.Nonnull;
import java.util.Optional;

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

    public static Optional<AvailableProcessor> fromReference(@Nonnull String reference) {
        for (AvailableProcessor processor : AvailableProcessor.values()) {
            if (processor.reference.equals(reference)) {
                return Optional.of(processor);
            }
        }
        return Optional.empty();
    }

}
