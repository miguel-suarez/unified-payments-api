package com.fun.driven.development.fun.unified.payments.api.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodTokenDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodToken}.
 */
public interface PaymentMethodTokenService {

    PaymentMethodTokenDTO save(PaymentMethodTokenDTO paymentMethodTokenDTO);

    List<PaymentMethodTokenDTO> findAll();

    Optional<PaymentMethodTokenDTO> findOne(Long id);

    void delete(Long id);
}
