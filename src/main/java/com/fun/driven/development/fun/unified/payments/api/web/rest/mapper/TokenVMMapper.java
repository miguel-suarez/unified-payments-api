package com.fun.driven.development.fun.unified.payments.api.web.rest.mapper;

import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.TokenVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TokenVMMapper {

    @Mappings({
        @Mapping(target = "token", source = "token"),
        @Mapping(target = "validUntil", source = "validUntil")
    })
    TokenVM fromDto(UnifiedPaymentTokenDTO token);

}
