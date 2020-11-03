package com.fun.driven.development.fun.unified.payments.vault;

import com.fun.driven.development.fun.unified.payments.api.FunUnifiedPaymentsApiApp;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest(classes = FunUnifiedPaymentsApiApp.class)
class TokenGeneratorTest {

    @Autowired
    private TokenGenerator generator;

    @Test
    void testTokenUniqueness() {
        List<String> tokens = new ArrayList<>();

        for (int i = 1; i<= 15000; i++) {
            UnifiedPaymentTokenDTO token = generator.newToken();
            Assertions.assertFalse(tokens.contains(token.getToken()), "Found duplicated token");
            tokens.add(token.getToken());
        }
    }
}
