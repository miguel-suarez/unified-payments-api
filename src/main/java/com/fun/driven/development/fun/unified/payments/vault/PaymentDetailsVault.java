package com.fun.driven.development.fun.unified.payments.vault;

import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;

public interface PaymentDetailsVault {

    UnifiedPaymentTokenDTO tokenize(Long merchantId, Card card);

    Card retrieveCard(String token);
}
