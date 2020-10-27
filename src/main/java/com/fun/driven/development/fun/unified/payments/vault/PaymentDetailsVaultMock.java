package com.fun.driven.development.fun.unified.payments.vault;

public class PaymentDetailsVaultMock {

    public UnifiedToken tokenize(Card card) {
        //TODO implement
        return new UnifiedToken("abcdef123456");
    }

    public Card retrieveCard(UnifiedToken token) {
        //TODO implement
        return new Card().setNumber("4111111111111112")
                         .setExpirationMonth("05")
                         .setExpirationYear("2030");
    }
}
