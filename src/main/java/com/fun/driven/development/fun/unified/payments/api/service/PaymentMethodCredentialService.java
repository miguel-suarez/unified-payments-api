package com.fun.driven.development.fun.unified.payments.api.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodCredentialDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodCredential}.
 */
public interface PaymentMethodCredentialService {

    PaymentMethodCredentialDTO save(PaymentMethodCredentialDTO paymentMethodCredentialDTO);

    List<PaymentMethodCredentialDTO> findAll();

    Optional<PaymentMethodCredentialDTO> findOne(Long id);

    void delete(Long id);
}
