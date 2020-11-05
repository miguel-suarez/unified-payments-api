package com.fun.driven.development.fun.unified.payments.vault.service.impl;

import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.vault.service.TokenGenerator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class TokenGeneratorImpl implements TokenGenerator {

    private static final int LEN = 22;
    private static final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public UnifiedPaymentTokenDTO newToken() {
        StringBuilder stringBuilder = new StringBuilder(LEN);
        for( int i = 0; i < LEN; i++ ) {
            stringBuilder.append(ALPHA_NUM.charAt(secureRandom.nextInt(ALPHA_NUM.length())));
        }
        String token = stringBuilder.toString();
        return new UnifiedPaymentTokenDTO().token(token);
    }
}
