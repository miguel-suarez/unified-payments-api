package com.fun.driven.development.fun.unified.payments.gateway.processors;

import com.fun.driven.development.fun.unified.payments.api.FunUnifiedPaymentsApiApp;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleRequest;
import com.fun.driven.development.fun.unified.payments.gateway.core.SaleResult;
import com.fun.driven.development.fun.unified.payments.vault.UnifiedToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
@SpringBootTest(classes = FunUnifiedPaymentsApiApp.class)
@Transactional
class BraintreeSaleProcessorTest {

    @Autowired
    private BraintreeSaleProcessor braintreeSaleProcessor;

    @Test
    void attemptSaleWithWrongCredentials() {
        String merchantCredentials = "{\"merchantId\":\"invalid\",\"publicKey\":\"invalid\"," +
            "\"privateKey\":\"invalid\"}";
        SaleRequest request = new SaleRequest().amountInCents(100L)
                                               .currencyIsoCode("EUR")
                                               .merchantCredentialsJson(merchantCredentials)
                                               .token(new UnifiedToken("abc"))
                                               .reference("12345");
        SaleResult result = braintreeSaleProcessor.sale(request);
        assertNotNull(result);
        assertEquals(SaleResult.ResultCode.ERROR, result.getResultCode());
        assertTrue(result.getReference().startsWith("19999"));
        assertEquals(20, result.getReference().length());
        assertNotNull(result.getResultDescription());
    }

    //TODO implement other cases after the Vault is implemented

}
