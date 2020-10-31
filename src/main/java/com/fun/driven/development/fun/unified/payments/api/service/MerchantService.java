package com.fun.driven.development.fun.unified.payments.api.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.MerchantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fun.driven.development.fun.unified.payments.api.domain.Merchant}.
 */
public interface MerchantService {

    MerchantDTO save(MerchantDTO merchantDTO);

    List<MerchantDTO> findAll();

    Page<MerchantDTO> findAllWithEagerRelationships(Pageable pageable);

    Optional<MerchantDTO> findOne(Long id);

    Optional<MerchantDTO> findOneByReference(String reference);

    boolean isNotAuthorizedUserOwnedByMerchant(String reference);

    void delete(Long id);
}
