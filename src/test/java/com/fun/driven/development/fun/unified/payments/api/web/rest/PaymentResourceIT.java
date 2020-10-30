package com.fun.driven.development.fun.unified.payments.api.web.rest;

import com.fun.driven.development.fun.unified.payments.api.FunUnifiedPaymentsApiApp;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleVM;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@SpringBootTest(classes = FunUnifiedPaymentsApiApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentResourceIT {

    private static final String ENDPOINT_URL = "/api/v1/unified/payments/sale";

    @Autowired
    private MockMvc restMerchantMockMvc;

    @Test
    @Transactional
    void sale() throws Exception {
        SaleVM saleVM = new SaleVM().amountInCents(100L)
                                    .currencyIsoCode("EUR")
                                    .token("Meadow");
        restMerchantMockMvc.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(saleVM))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                           .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    // If no currencyCode is given, EUR is used as a default value
    void saleWithoutCurrencyCode() throws Exception {
        SaleVM saleVM = new SaleVM().amountInCents(100L)
                                    .token("Meadow");
        restMerchantMockMvc.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(saleVM))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                           .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @WithAnonymousUser
    void saleWithoutBasicAuthentication() throws Exception {
        SaleVM saleVM = new SaleVM().amountInCents(100L)
                                    .currencyIsoCode("EUR")
                                    .token("Meadow");
        restMerchantMockMvc.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(saleVM))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                           .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void saleWithWrongMerchantReference() throws Exception {
        SaleVM saleVM = new SaleVM().amountInCents(100L)
                                    .currencyIsoCode("EUR")
                                    .token("Meadow");
        restMerchantMockMvc.perform(post((ENDPOINT_URL))
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content(TestUtil.convertObjectToJsonBytes(saleVM))
                                   .header(Constants.MERCHANT_HEADER,"xyz"))
                           .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void saleWithoutAmount() throws Exception {
        SaleVM saleVM = new SaleVM().amountInCents(null)
                                    .currencyIsoCode("EUR")
                                    .token("Meadow");
        restMerchantMockMvc.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(saleVM))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                           .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void saleWithoutToken() throws Exception {
        SaleVM saleVM = new SaleVM().amountInCents(100L)
                                    .currencyIsoCode("EUR");
        restMerchantMockMvc.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(saleVM))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                           .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void saleWithInvalidToken() throws Exception {
        SaleVM saleVM = new SaleVM().amountInCents(100L)
                                    .currencyIsoCode("EUR")
                                    .token("invalid");
        restMerchantMockMvc.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(saleVM))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                           .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void saleWithInvalidCurrency() throws Exception {
        SaleVM saleVM = new SaleVM().amountInCents(100L)
                                    .currencyIsoCode("OOO")
                                    .token("Meadow");
        restMerchantMockMvc.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(saleVM))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                           .andExpect(status().isBadRequest());
    }
}
