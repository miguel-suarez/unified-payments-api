package com.fun.driven.development.fun.unified.payments.api.service.mapper;

import com.fun.driven.development.fun.unified.payments.api.domain.UnifiedPaymentToken;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link UnifiedPaymentToken} and its DTO {@link UnifiedPaymentTokenDTO}.
 */
@Mapper(componentModel = "spring", uses = {MerchantMapper.class})
public interface UnifiedPaymentTokenMapper extends EntityMapper<UnifiedPaymentTokenDTO, UnifiedPaymentToken> {

    @Mapping(source = "merchant.id", target = "merchantId")
    UnifiedPaymentTokenDTO toDto(UnifiedPaymentToken unifiedPaymentToken);

    @Mapping(source = "merchantId", target = "merchant")
    UnifiedPaymentToken toEntity(UnifiedPaymentTokenDTO unifiedPaymentTokenDTO);

    default UnifiedPaymentToken fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnifiedPaymentToken unifiedPaymentToken = new UnifiedPaymentToken();
        unifiedPaymentToken.setId(id);
        return unifiedPaymentToken;
    }
}
