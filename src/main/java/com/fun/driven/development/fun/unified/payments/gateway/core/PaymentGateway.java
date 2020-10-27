package com.fun.driven.development.fun.unified.payments.gateway.core;

import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class PaymentGateway {

    private final HashMap<String, SaleProcessor> paymentProcessors = new HashMap<>();

    public void add(String name, SaleProcessor saleProcessor) {
        paymentProcessors.put(name, saleProcessor);
    }

    public SaleProcessor retrieve(String name) {
        SaleProcessor saleProcessor = paymentProcessors.get(name);

        if (saleProcessor == null) {
            throw new IllegalStateException("No payment processor registered for " + name);
        }
        return saleProcessor;
    }

}
