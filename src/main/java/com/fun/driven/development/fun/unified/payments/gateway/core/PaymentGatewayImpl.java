package com.fun.driven.development.fun.unified.payments.gateway.core;

import com.fun.driven.development.fun.unified.payments.gateway.processors.BraintreeSaleProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@SuppressWarnings("rawtypes")
@Service
public class PaymentGatewayImpl {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayImpl.class);

    @Autowired
    private BraintreeSaleProcessor braintreeSaleProcessor;

    private final HashMap<AvailableProcessor, SaleProcessor> paymentProcessors = new HashMap<>();

    public PaymentGatewayImpl() {
        paymentProcessors.put(AvailableProcessor.BRAINTREE, braintreeSaleProcessor);
    }

    public SaleProcessor using(AvailableProcessor processor) {
        SaleProcessor saleProcessor = paymentProcessors.get(processor);

        if (saleProcessor == null) {
            throw new IllegalStateException("No available processor registered with " + processor.name());
        }
        return saleProcessor;
    }

}
