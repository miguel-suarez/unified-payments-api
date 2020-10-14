package com.fun.driven.development.fun.unified.payments.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "payment_method_credential")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentMethodCredential implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "api_key", length = 500, nullable = false)
    private String apiKey;

    @Column(name = "valid_until")
    private Instant validUntil;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentMethodCredentials", allowSetters = true)
    private Merchant merchant;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "paymentMethodCredentials", allowSetters = true)
    private PaymentMethod paymentMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public PaymentMethodCredential apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public PaymentMethodCredential validUntil(Instant validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public PaymentMethodCredential merchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentMethodCredential paymentMethod(PaymentMethod paymentMethod) {
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
        if (!(o instanceof PaymentMethodCredential)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethodCredential) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodCredential{" +
            "id=" + getId() +
            ", apiKey='" + getApiKey() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            "}";
    }
}
