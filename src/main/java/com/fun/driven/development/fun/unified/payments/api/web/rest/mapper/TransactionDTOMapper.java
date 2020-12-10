package com.fun.driven.development.fun.unified.payments.api.web.rest.mapper;

import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.TransactionType;
import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.UnifiedTransactionResult;
import com.fun.driven.development.fun.unified.payments.api.service.dto.TransactionDTO;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleRequestVM;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleResultVM;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TransactionDTOMapper {

    public TransactionDTO toDTO(SaleRequestVM request, SaleResultVM result) {
        TransactionDTO tx = new TransactionDTO();
        tx.setAmount(request.getAmountInCents());
        tx.setFunReference(result.getReference());
        tx.setTransactionType(TransactionType.SALE);
        tx.setTransactionDate(Instant.now());
        tx.setResult(UnifiedTransactionResult.valueOf(result.getResultCode().name()));
        tx.setProcessorResult(result.getProcessorResult());
        tx.setExternalReference(request.getExternalReference());
        return tx;
    }

}
