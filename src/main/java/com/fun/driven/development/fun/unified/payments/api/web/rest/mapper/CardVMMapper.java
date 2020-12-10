package com.fun.driven.development.fun.unified.payments.api.web.rest.mapper;

import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.CardVM;
import com.fun.driven.development.fun.unified.payments.vault.domain.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardVMMapper extends VMMapper<CardVM, Card> {
}
