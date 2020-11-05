package com.fun.driven.development.fun.unified.payments.vault.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;

public interface TokenGenerator {

    UnifiedPaymentTokenDTO newToken();
}
