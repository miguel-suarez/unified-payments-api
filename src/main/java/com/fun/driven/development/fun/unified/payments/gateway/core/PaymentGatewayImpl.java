package com.fun.driven.development.fun.unified.payments.gateway.core;

import com.fun.driven.development.fun.unified.payments.gateway.processors.BraintreeSaleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.HashMap;

@SuppressWarnings("rawtypes")
@Service
public class PaymentGatewayImpl implements PaymentGateway {

    @Autowired
    private BraintreeSaleProcessor braintreeSaleProcessor;

    private final HashMap<AvailableProcessor, SaleProcessor> paymentProcessors = new EnumMap<>();

    @PostConstruct
    private void setUp() {
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
