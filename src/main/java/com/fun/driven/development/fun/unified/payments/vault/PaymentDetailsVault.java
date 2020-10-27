package com.fun.driven.development.fun.unified.payments.vault;

public interface PaymentDetailsVault {

    UnifiedToken tokenize(Card card);

    Card retrieveCard(UnifiedToken token);
}
