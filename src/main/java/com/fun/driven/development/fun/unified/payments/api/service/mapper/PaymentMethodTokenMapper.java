package com.fun.driven.development.fun.unified.payments.api.service.mapper;

import com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodToken;
import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodTokenDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link PaymentMethodToken} and its DTO {@link PaymentMethodTokenDTO}.
 */
@Mapper(componentModel = "spring", uses = {MerchantMapper.class})
public interface PaymentMethodTokenMapper extends EntityMapper<PaymentMethodTokenDTO, PaymentMethodToken> {

    @Mapping(source = "merchant.id", target = "merchantId")
    PaymentMethodTokenDTO toDto(PaymentMethodToken paymentMethodToken);

    @Mapping(source = "merchantId", target = "merchant")
    PaymentMethodToken toEntity(PaymentMethodTokenDTO paymentMethodTokenDTO);

    default PaymentMethodToken fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentMethodToken paymentMethodToken = new PaymentMethodToken();
        paymentMethodToken.setId(id);
        return paymentMethodToken;
    }
}
