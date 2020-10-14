package com.fun.driven.development.fun.unified.payments.api.repository;

import com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodUnavailable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface PaymentMethodUnavailableRepository extends JpaRepository<PaymentMethodUnavailable, Long> {
}
