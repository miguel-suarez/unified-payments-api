package com.fun.driven.development.fun.unified.payments.api.service;

import com.fun.driven.development.fun.unified.payments.api.service.dto.TransactionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fun.driven.development.fun.unified.payments.api.domain.Transaction}.
 */
public interface TransactionService {

    TransactionDTO save(TransactionDTO transactionDTO);

    List<TransactionDTO> findAll();

    Optional<TransactionDTO> findOne(Long id);

    void delete(Long id);
}
