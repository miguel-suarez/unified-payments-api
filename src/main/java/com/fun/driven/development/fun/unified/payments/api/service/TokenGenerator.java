package com.fun.driven.development.fun.unified.payments.api.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.CardVM;

public interface TokenGenerator {

    UnifiedPaymentTokenDTO of(CardVM card);
}
