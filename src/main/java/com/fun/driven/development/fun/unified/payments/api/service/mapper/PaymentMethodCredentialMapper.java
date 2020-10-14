package com.fun.driven.development.fun.unified.payments.api.service.mapper;

import com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodCredential;
import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodCredentialDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link PaymentMethodCredential} and its DTO {@link PaymentMethodCredentialDTO}.
 */
@Mapper(componentModel = "spring", uses = {MerchantMapper.class, PaymentMethodMapper.class})
public interface PaymentMethodCredentialMapper extends EntityMapper<PaymentMethodCredentialDTO, PaymentMethodCredential> {

    @Mapping(source = "merchant.id", target = "merchantId")
    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    PaymentMethodCredentialDTO toDto(PaymentMethodCredential paymentMethodCredential);

    @Mapping(source = "merchantId", target = "merchant")
    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    PaymentMethodCredential toEntity(PaymentMethodCredentialDTO paymentMethodCredentialDTO);

    default PaymentMethodCredential fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentMethodCredential paymentMethodCredential = new PaymentMethodCredential();
        paymentMethodCredential.setId(id);
        return paymentMethodCredential;
    }
}
