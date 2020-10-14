package com.fun.driven.development.fun.unified.payments.api.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodUnavailableDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodUnavailable}.
 */
public interface PaymentMethodUnavailableService {

    PaymentMethodUnavailableDTO save(PaymentMethodUnavailableDTO paymentMethodUnavailableDTO);

    List<PaymentMethodUnavailableDTO> findAll();

    Optional<PaymentMethodUnavailableDTO> findOne(Long id);

    void delete(Long id);
}
