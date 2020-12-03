package com.fun.driven.development.fun.unified.payments.vault.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.vault.domain.Card;

import java.util.Optional;

public interface PaymentDetailsVault {

    UnifiedPaymentTokenDTO tokenize(long merchantId, Card card);

    Optional<Card> retrieveCard(long merchantId, String token);
}
