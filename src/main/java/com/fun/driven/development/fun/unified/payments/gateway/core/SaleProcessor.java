package com.fun.driven.development.fun.unified.payments.gateway.core;

public interface SaleProcessor<I, O, C> {

    C loadMerchantCredentials(SaleRequest request);

    I toThirdPartyRequest(SaleRequest request);

    O thirdPartySale(I request, C credentials);

    SaleResult toUnifiedResult(O thirdPartyResult);

    default SaleResult sale(SaleRequest request) {
        I input = toThirdPartyRequest(request);
        C credentials = loadMerchantCredentials(request);
        O output = thirdPartySale(input, credentials);
        return toUnifiedResult(output);
    }
}
