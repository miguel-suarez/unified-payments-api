package com.fun.driven.development.fun.unified.payments.api.web.rest.mapper;

import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.CardVM;
import com.fun.driven.development.fun.unified.payments.vault.domain.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CardVMMapper {

    @Mappings({
        @Mapping(target = "number", source = "cardNumber"),
        @Mapping(target = "expirationMonth", source = "expiryMonth"),
        @Mapping(target = "expirationYear", source = "expiryYear")
    })
    Card toEntity(CardVM vm);
}
