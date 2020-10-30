package com.fun.driven.development.fun.unified.payments.api.repository;

import com.fun.driven.development.fun.unified.payments.api.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByIsoCode(String isoCode);
}
