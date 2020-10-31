package com.fun.driven.development.fun.unified.payments.api.web.rest;

import com.fun.driven.development.fun.unified.payments.api.FunUnifiedPaymentsApiApp;
import com.fun.driven.development.fun.unified.payments.api.service.UnifiedPaymentTokenService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.CardVM;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link TokenResource} REST controller.
 */
@SpringBootTest(classes = FunUnifiedPaymentsApiApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class TokenResourceIT {

    private static final String ENDPOINT_URL = "/api/v1/unified/tokens";

    @Autowired
    private MockMvc restTokenMock;

    @Autowired
    private UnifiedPaymentTokenService unifiedPaymentTokenService;

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void tokenize() throws Exception {
        int tableSizeBefore = unifiedPaymentTokenService.findAll().size();

        CardVM request = new CardVM().cardNumber("370000000000002")
                                     .expiryMonth(1)
                                     .expiryYear(2025);

        restTokenMock.perform(post((ENDPOINT_URL))
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(TestUtil.convertObjectToJsonBytes(request))
                                 .header(Constants.MERCHANT_HEADER,"hardware"))
                     .andExpect(status().isOk());

        List<UnifiedPaymentTokenDTO> tokens = unifiedPaymentTokenService.findAll();
        assertThat(tokens).hasSize(tableSizeBefore + 1);
        UnifiedPaymentTokenDTO token = tokens.get(tokens.size() -1);
        assertThat(token.getMerchantId()).isEqualTo(1L);
        assertThat(token.getToken()).hasSize(5);
        assertThat(token.getToken()).startsWith("abc");
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void tokenizeWithInvalidCardNumber() throws Exception {
        CardVM request = new CardVM().cardNumber("54654654665")
                                     .expiryMonth(1)
                                     .expiryYear(2025);

        restTokenMock.perform(post((ENDPOINT_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(request))
                                .header(Constants.MERCHANT_HEADER,"hardware"))
                     .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void tokenizeWithExpired() throws Exception {
        CardVM request = new CardVM().cardNumber("6703444444444449")
                                     .expiryMonth(9)
                                     .expiryYear(2020);

        restTokenMock.perform(post((ENDPOINT_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(request))
                                .header(Constants.MERCHANT_HEADER,"hardware"))
                     .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void tokenizeWithoutCardNumber() throws Exception {
        CardVM request = new CardVM().expiryMonth(1)
                                     .expiryYear(2025);

        restTokenMock.perform(post((ENDPOINT_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(request))
                                .header(Constants.MERCHANT_HEADER,"hardware"))
                     .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void tokenizeWithoutExpiryMonth() throws Exception {
        CardVM request = new CardVM().cardNumber("370000000000002")
                                     .expiryYear(2025);

        restTokenMock.perform(post((ENDPOINT_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(request))
                                .header(Constants.MERCHANT_HEADER,"hardware"))
                     .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void tokenizeWithoutExpiryYear() throws Exception {
        CardVM request = new CardVM().cardNumber("370000000000002")
                                     .expiryMonth(1);

        restTokenMock.perform(post((ENDPOINT_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(request))
                                .header(Constants.MERCHANT_HEADER,"hardware"))
                     .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithAnonymousUser
    void tokenizeWithoutBasicAuthentication() throws Exception {
        CardVM request = new CardVM().cardNumber("370000000000002")
                                     .expiryMonth(1)
                                     .expiryYear(2025);

        restTokenMock.perform(post((ENDPOINT_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(request))
                                .header(Constants.MERCHANT_HEADER,"hardware"))
                     .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void tokenizeWithWrongMerchantReference() throws Exception {
        CardVM request = new CardVM().cardNumber("370000000000002")
                                     .expiryMonth(1)
                                     .expiryYear(2025);

        restTokenMock.perform(post((ENDPOINT_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(request))
                                .header(Constants.MERCHANT_HEADER,"xyz"))
                     .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    void tokenizeWithWrongMerchantUserCombination() throws Exception {
        CardVM request = new CardVM().cardNumber("370000000000002")
                                     .expiryMonth(1)
                                     .expiryYear(2025);

        restTokenMock.perform(post((ENDPOINT_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(request))
                                .header(Constants.MERCHANT_HEADER,"hardware"))
                     .andExpect(status().isForbidden());
    }
}
