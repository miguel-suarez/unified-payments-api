package com.fun.driven.development.fun.unified.payments.api.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethod}.
 */
public interface PaymentMethodService {

    PaymentMethodDTO save(PaymentMethodDTO paymentMethodDTO);

    List<PaymentMethodDTO> findAll();

    Optional<PaymentMethodDTO> findOne(Long id);

    void delete(Long id);
}
