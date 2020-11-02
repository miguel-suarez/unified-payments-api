package com.fun.driven.development.fun.unified.payments.api.service.impl;

import com.fun.driven.development.fun.unified.payments.api.service.TokenGenerator;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class TokenGeneratorImpl implements TokenGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public UnifiedPaymentTokenDTO newToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        String token = new String(Hex.encode(randomBytes));
        return new UnifiedPaymentTokenDTO().token(token);
    }
}
