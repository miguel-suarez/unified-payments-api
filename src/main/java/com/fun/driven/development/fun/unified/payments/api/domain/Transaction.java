package com.fun.driven.development.fun.unified.payments.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

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
    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

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

    @OneToOne
    @JoinColumn(unique = true)
    private PaymentMethodToken paymentMethodToken;

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

    public PaymentMethodToken getPaymentMethodToken() {
        return paymentMethodToken;
    }

    public Transaction paymentMethodToken(PaymentMethodToken paymentMethodToken) {
        this.paymentMethodToken = paymentMethodToken;
        return this;
    }

    public void setPaymentMethodToken(PaymentMethodToken paymentMethodToken) {
        this.paymentMethodToken = paymentMethodToken;
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
            ", transactionDate='" + getTransactionDate() + "'" +
            ", externalReference='" + getExternalReference() + "'" +
            "}";
    }
}
