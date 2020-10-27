package com.fun.driven.development.fun.unified.payments.api.service.mapper;

import com.fun.driven.development.fun.unified.payments.api.domain.Merchant;
import com.fun.driven.development.fun.unified.payments.api.service.dto.MerchantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Merchant} and its DTO {@link MerchantDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PaymentMethodMapper.class})
public interface MerchantMapper extends EntityMapper<MerchantDTO, Merchant> {

    @Mapping(target = "paymentMethodCredentials", ignore = true)
    @Mapping(target = "removePaymentMethodCredential", ignore = true)
    @Mapping(target = "unifiedPaymentTokens", ignore = true)
    @Mapping(target = "removeUnifiedPaymentToken", ignore = true)
    @Mapping(target = "removeUser", ignore = true)
    @Mapping(target = "removePaymentMethod", ignore = true)
    Merchant toEntity(MerchantDTO merchantDTO);

    default Merchant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Merchant merchant = new Merchant();
        merchant.setId(id);
        return merchant;
    }
}
