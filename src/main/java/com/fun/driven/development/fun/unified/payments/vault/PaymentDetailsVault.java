package com.fun.driven.development.fun.unified.payments.vault;

import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;

import java.util.Optional;

public interface PaymentDetailsVault {

    UnifiedPaymentTokenDTO tokenize(long merchantId, Card card);

    Optional<Card> retrieveCard(long merchantId, String token);
}
