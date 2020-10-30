package com.fun.driven.development.fun.unified.payments.api.repository;

import com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentMethodCredentialRepository extends JpaRepository<PaymentMethodCredential, Long> {

    Optional<PaymentMethodCredential> findOneByPaymentMethodIdAndMerchantId(Long paymentMethodId, Long merchantId);
}
