package com.fun.driven.development.fun.unified.payments.api.service.mapper;

import com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodUnavailable;
import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodUnavailableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link PaymentMethodUnavailable} and its DTO {@link PaymentMethodUnavailableDTO}.
 */
@Mapper(componentModel = "spring", uses = {PaymentMethodMapper.class})
public interface PaymentMethodUnavailableMapper extends EntityMapper<PaymentMethodUnavailableDTO, PaymentMethodUnavailable> {

    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    PaymentMethodUnavailableDTO toDto(PaymentMethodUnavailable paymentMethodUnavailable);

    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    PaymentMethodUnavailable toEntity(PaymentMethodUnavailableDTO paymentMethodUnavailableDTO);

    default PaymentMethodUnavailable fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentMethodUnavailable paymentMethodUnavailable = new PaymentMethodUnavailable();
        paymentMethodUnavailable.setId(id);
        return paymentMethodUnavailable;
    }
}
