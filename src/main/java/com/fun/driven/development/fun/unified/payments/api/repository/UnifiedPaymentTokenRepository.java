package com.fun.driven.development.fun.unified.payments.api.repository;

import com.fun.driven.development.fun.unified.payments.api.domain.UnifiedPaymentToken;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface UnifiedPaymentTokenRepository extends JpaRepository<UnifiedPaymentToken, Long> {
}
