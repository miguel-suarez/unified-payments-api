package com.fun.driven.development.fun.unified.payments.api.repository;

import com.fun.driven.development.fun.unified.payments.api.domain.UnifiedPaymentToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnifiedPaymentTokenRepository extends JpaRepository<UnifiedPaymentToken, Long> {

    Optional<UnifiedPaymentToken> findByToken(@Param("token") String token);
}
