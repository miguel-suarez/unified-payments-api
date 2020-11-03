package com.fun.driven.development.fun.unified.payments.api.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fun.driven.development.fun.unified.payments.api.domain.UnifiedPaymentToken}.
 */
public interface UnifiedPaymentTokenService {

    UnifiedPaymentTokenDTO save(UnifiedPaymentTokenDTO unifiedPaymentTokenDTO);

    List<UnifiedPaymentTokenDTO> findAll();

    Optional<UnifiedPaymentTokenDTO> findOne(Long id);

    Optional<UnifiedPaymentTokenDTO> findOneByTokenAndMerchantId(String token, Long merchantId);

    Optional<UnifiedPaymentTokenDTO> findByPayloadAndMerchantId(String payload, long merchantId);

    void delete(Long id);
}
