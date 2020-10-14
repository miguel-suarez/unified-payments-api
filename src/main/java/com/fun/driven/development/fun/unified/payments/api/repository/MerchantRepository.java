package com.fun.driven.development.fun.unified.payments.api.repository;

import com.fun.driven.development.fun.unified.payments.api.domain.Merchant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @Query(value = "select distinct merchant from Merchant merchant left join fetch merchant.paymentMethods",
        countQuery = "select count(distinct merchant) from Merchant merchant")
    Page<Merchant> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct merchant from Merchant merchant left join fetch merchant.paymentMethods")
    List<Merchant> findAllWithEagerRelationships();

    @Query("select merchant from Merchant merchant left join fetch merchant.paymentMethods where merchant.id =:id")
    Optional<Merchant> findOneWithEagerRelationships(@Param("id") Long id);
}
