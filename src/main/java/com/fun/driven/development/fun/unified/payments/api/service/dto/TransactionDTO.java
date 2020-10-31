package com.fun.driven.development.fun.unified.payments.api.service.dto;

import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.TransactionType;
import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.UnifiedTransactionResult;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleRequestVM;
import com.fun.driven.development.fun.unified.payments.api.web.rest.vm.SaleResultVM;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.fun.driven.development.fun.unified.payments.api.domain.Transaction} entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private Long amount;

    @NotNull
    @Size(max = 25)
    private String funReference;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private Instant transactionDate;

    private UnifiedTransactionResult result;

    private String processorResult;

    @Size(max = 25)
    private String externalReference;

    private Long merchantId;

    private Long currencyId;

    private Long unifiedPaymentTokenId;

    private Long paymentMethodId;

    public static TransactionDTO fromSale(SaleRequestVM request, SaleResultVM result) {
        TransactionDTO tx = new TransactionDTO();
        tx.amount = request.getAmountInCents();
        tx.funReference = result.getReference();
        tx.transactionType = TransactionType.Sale;
        tx.transactionDate = Instant.now();
        tx.result = UnifiedTransactionResult.valueOf(result.getResultCode().name());
        tx.processorResult = result.getProcessorResult();
        tx.externalReference = request.getExternalReference();
        return tx;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getFunReference() {
        return funReference;
    }

    public void setFunReference(String funReference) {
        this.funReference = funReference;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public UnifiedTransactionResult getResult() {
        return result;
    }

    public void setResult(UnifiedTransactionResult result) {
        this.result = result;
    }

    public String getProcessorResult() {
        return processorResult;
    }

    public void setProcessorResult(String processorResult) {
        this.processorResult = processorResult;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Long getUnifiedPaymentTokenId() {
        return unifiedPaymentTokenId;
    }

    public void setUnifiedPaymentTokenId(Long unifiedPaymentTokenId) {
        this.unifiedPaymentTokenId = unifiedPaymentTokenId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        return id != null && id.equals(((TransactionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", funReference='" + getFunReference() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", result='" + getResult() + "'" +
            ", processorResult='" + getProcessorResult() + "'" +
            ", externalReference='" + getExternalReference() + "'" +
            ", merchantId=" + getMerchantId() +
            ", currencyId=" + getCurrencyId() +
            ", unifiedPaymentTokenId=" + getUnifiedPaymentTokenId() +
            ", paymentMethodId=" + getPaymentMethodId() +
            "}";
    }
}
