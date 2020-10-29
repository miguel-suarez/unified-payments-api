package com.fun.driven.development.fun.unified.payments.vault;

import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsVaultMock implements PaymentDetailsVault {

    public UnifiedToken tokenize(Card card) {
        //TODO implement
        return new UnifiedToken("abcdef123456");
    }

    public Card retrieveCard(UnifiedToken token) {
        //TODO implement
        return new Card().setNumber("4111111111111111")
                         .setExpirationMonth(5)
                         .setExpirationYear(2030);
    }
}
