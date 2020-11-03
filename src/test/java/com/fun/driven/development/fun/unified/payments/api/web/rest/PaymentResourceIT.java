package com.fun.driven.development.fun.unified.payments.api.web.rest;

import com.fun.driven.development.fun.unified.payments.api.FunUnifiedPaymentsApiApp;
import com.fun.driven.development.fun.unified.payments.api.service.TransactionService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.TransactionDTO;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleRequestVM;
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
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@SpringBootTest(classes = FunUnifiedPaymentsApiApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class PaymentResourceIT {

    private static final String ENDPOINT_URL = "/api/v1/unified/payments/sale";

    @Autowired
    private MockMvc restPaymentMock;

    @Autowired
    private TransactionService transactionService;

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void sale() throws Exception {
        int tableSizeBefore = transactionService.findAll().size();

        SaleRequestVM request = new SaleRequestVM().amountInCents(100L)
                                                   .currencyIsoCode("EUR")
                                                   .token("Racket");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(request))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                       .andExpect(status().isOk());

        List<TransactionDTO> transactions = transactionService.findAll();
        assertThat(transactions).hasSize(tableSizeBefore + 1);
        TransactionDTO transaction = transactions.get(transactions.size() -1);
        assertThat(transaction.getAmount()).isEqualTo(100L);
        assertThat(transaction.getProcessorResult()).isNotEmpty();
        assertThat(transaction.getFunReference()).isNotEmpty();
        //TODO validate the rest of the fields to make sure the tx is created properly
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    // If no currencyCode is given, EUR is used as a default value
    void saleWithoutCurrencyCode() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(101L)
                                                   .token("Racket");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(request))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                       .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithAnonymousUser
    void saleWithoutBasicAuthentication() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(100L)
                                                   .currencyIsoCode("EUR")
                                                   .token("Racket");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(request))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                       .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void saleWithWrongMerchantReference() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(100L)
                                                   .currencyIsoCode("EUR")
                                                   .token("Racket");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content(TestUtil.convertObjectToJsonBytes(request))
                                   .header(Constants.MERCHANT_HEADER,"xyz"))
                       .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void saleWithWrongPaymentProcessor() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(102L)
                                                   .currencyIsoCode("EUR")
                                                   .paymentProcessor("invalid")
                                                   .token("Racket");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content(TestUtil.convertObjectToJsonBytes(request))
                                   .header(Constants.MERCHANT_HEADER,"hardware"))
                       .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    void saleWithWrongMerchantCredentials() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(100L)
                                                   .currencyIsoCode("EUR")
                                                   .token("Raspberry");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(request))
                                    .header(Constants.MERCHANT_HEADER,"atom"))
                       .andExpect(status().is5xxServerError());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    void saleWithWrongMerchantUserCombination() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(100L)
                                                   .currencyIsoCode("EUR")
                                                   .token("Racket");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(request))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                       .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void saleWithoutAmount() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(null)
                                                   .currencyIsoCode("EUR")
                                                   .token("Racket");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(request))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                       .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void saleWithTokenOfDifferentMerchant() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(102L)
                                                   .currencyIsoCode("EUR")
                                                   .token("Raspberry");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(request))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                       .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void saleWithoutToken() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(100L)
                                                   .currencyIsoCode("EUR");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(request))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                       .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void saleWithInvalidToken() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(100L)
                                                   .currencyIsoCode("EUR")
                                                   .token("invalid");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(request))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                       .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails(value = "system", userDetailsServiceBeanName = "userDetailsService")
    void saleWithInvalidCurrency() throws Exception {
        SaleRequestVM request = new SaleRequestVM().amountInCents(100L)
                                                   .currencyIsoCode("OOO")
                                                   .token("Racket");
        restPaymentMock.perform(post((ENDPOINT_URL))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtil.convertObjectToJsonBytes(request))
                                    .header(Constants.MERCHANT_HEADER,"hardware"))
                       .andExpect(status().isBadRequest());
    }
}
