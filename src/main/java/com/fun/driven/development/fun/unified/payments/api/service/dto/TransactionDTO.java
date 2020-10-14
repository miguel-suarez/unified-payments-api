package com.fun.driven.development.fun.unified.payments.api.service.dto;

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
    private Instant transactionDate;

    @Size(max = 25)
    private String externalReference;

    private Long merchantId;

    private Long currencyId;

    private Long unifiedPaymentTokenId;

    private Long paymentMethodTokenId;

    private Long paymentMethodId;

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

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
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

    public Long getPaymentMethodTokenId() {
        return paymentMethodTokenId;
    }

    public void setPaymentMethodTokenId(Long paymentMethodTokenId) {
        this.paymentMethodTokenId = paymentMethodTokenId;
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
            ", transactionDate='" + getTransactionDate() + "'" +
            ", externalReference='" + getExternalReference() + "'" +
            ", merchantId=" + getMerchantId() +
            ", currencyId=" + getCurrencyId() +
            ", unifiedPaymentTokenId=" + getUnifiedPaymentTokenId() +
            ", paymentMethodTokenId=" + getPaymentMethodTokenId() +
            ", paymentMethodId=" + getPaymentMethodId() +
            "}";
    }
}
