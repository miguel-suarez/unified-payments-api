package com.fun.driven.development.fun.unified.payments.api.service.mapper;

import com.fun.driven.development.fun.unified.payments.api.domain.Transaction;
import com.fun.driven.development.fun.unified.payments.api.service.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {MerchantMapper.class, CurrencyMapper.class, UnifiedPaymentTokenMapper.class, PaymentMethodTokenMapper.class, PaymentMethodMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(source = "merchant.id", target = "merchantId")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "unifiedPaymentToken.id", target = "unifiedPaymentTokenId")
    @Mapping(source = "paymentMethodToken.id", target = "paymentMethodTokenId")
    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(source = "merchantId", target = "merchant")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "unifiedPaymentTokenId", target = "unifiedPaymentToken")
    @Mapping(source = "paymentMethodTokenId", target = "paymentMethodToken")
    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    Transaction toEntity(TransactionDTO transactionDTO);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
