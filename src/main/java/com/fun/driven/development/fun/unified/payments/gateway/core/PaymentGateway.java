package com.fun.driven.development.fun.unified.payments.gateway.core;

public interface PaymentGateway {

    SaleProcessor using(AvailableProcessor processor);
}
