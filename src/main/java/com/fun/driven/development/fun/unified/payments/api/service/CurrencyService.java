package com.fun.driven.development.fun.unified.payments.api.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.CurrencyDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fun.driven.development.fun.unified.payments.api.domain.Currency}.
 */
public interface CurrencyService {

    CurrencyDTO save(CurrencyDTO currencyDTO);

    List<CurrencyDTO> findAll();

    Optional<CurrencyDTO> findOne(Long id);

    Optional<CurrencyDTO> findOneByIsoCode(String isoCode);

    void delete(Long id);
}
