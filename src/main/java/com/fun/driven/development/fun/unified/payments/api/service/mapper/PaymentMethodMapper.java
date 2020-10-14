package com.fun.driven.development.fun.unified.payments.api.service.mapper;

import com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethod;
import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link PaymentMethod} and its DTO {@link PaymentMethodDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentMethodMapper extends EntityMapper<PaymentMethodDTO, PaymentMethod> {

    @Mapping(target = "paymentMethodUnavailables", ignore = true)
    @Mapping(target = "removePaymentMethodUnavailable", ignore = true)
    @Mapping(target = "paymentMethodCredentials", ignore = true)
    @Mapping(target = "removePaymentMethodCredential", ignore = true)
    @Mapping(target = "merchants", ignore = true)
    @Mapping(target = "removeMerchant", ignore = true)
    PaymentMethod toEntity(PaymentMethodDTO paymentMethodDTO);

    default PaymentMethod fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(id);
        return paymentMethod;
    }
}
