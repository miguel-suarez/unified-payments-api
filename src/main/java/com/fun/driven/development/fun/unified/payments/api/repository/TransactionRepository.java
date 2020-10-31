package com.fun.driven.development.fun.unified.payments.api.repository;

import com.fun.driven.development.fun.unified.payments.api.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByFunReference(@Param("funReference") String reference);
}
