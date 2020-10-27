package com.fun.driven.development.fun.unified.payments.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.TransactionType;
import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.UnifiedTransactionResult;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @NotNull
    @Size(max = 25)
    @Column(name = "fun_reference", length = 25, nullable = false, unique = true)
    private String funReference;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @NotNull
    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private UnifiedTransactionResult result;

    @Column(name = "processor_result")
    private String processorResult;

    @Size(max = 25)
    @Column(name = "external_reference", length = 25)
    private String externalReference;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Merchant merchant;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Currency currency;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private UnifiedPaymentToken unifiedPaymentToken;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private PaymentMethod paymentMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public Transaction amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getFunReference() {
        return funReference;
    }

    public Transaction funReference(String funReference) {
        this.funReference = funReference;
        return this;
    }

    public void setFunReference(String funReference) {
        this.funReference = funReference;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Transaction transactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public Transaction transactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public UnifiedTransactionResult getResult() {
        return result;
    }

    public Transaction result(UnifiedTransactionResult result) {
        this.result = result;
        return this;
    }

    public void setResult(UnifiedTransactionResult result) {
        this.result = result;
    }

    public String getProcessorResult() {
        return processorResult;
    }

    public Transaction processorResult(String processorResult) {
        this.processorResult = processorResult;
        return this;
    }

    public void setProcessorResult(String processorResult) {
        this.processorResult = processorResult;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public Transaction externalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public Transaction merchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Transaction currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public UnifiedPaymentToken getUnifiedPaymentToken() {
        return unifiedPaymentToken;
    }

    public Transaction unifiedPaymentToken(UnifiedPaymentToken unifiedPaymentToken) {
        this.unifiedPaymentToken = unifiedPaymentToken;
        return this;
    }

    public void setUnifiedPaymentToken(UnifiedPaymentToken unifiedPaymentToken) {
        this.unifiedPaymentToken = unifiedPaymentToken;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Transaction paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", funReference='" + getFunReference() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", result='" + getResult() + "'" +
            ", processorResult='" + getProcessorResult() + "'" +
            ", externalReference='" + getExternalReference() + "'" +
            "}";
    }
}
