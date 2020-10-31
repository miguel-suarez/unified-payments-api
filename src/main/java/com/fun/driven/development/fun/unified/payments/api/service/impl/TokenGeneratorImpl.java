package com.fun.driven.development.fun.unified.payments.api.service.impl;

import com.fun.driven.development.fun.unified.payments.api.service.TokenGenerator;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.CardVM;
import org.springframework.stereotype.Service;

@Service
public class TokenGeneratorImpl implements TokenGenerator {

    @Override
    public UnifiedPaymentTokenDTO of(CardVM card) {
        return new UnifiedPaymentTokenDTO().token("abced");
    }
}
