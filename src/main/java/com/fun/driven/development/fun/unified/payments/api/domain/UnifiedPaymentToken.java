package com.fun.driven.development.fun.unified.payments.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.PaymentMethodType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "unified_payment_token")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UnifiedPaymentToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "token", length = 500, nullable = false, unique = true)
    private String token;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PaymentMethodType type;

    @NotNull
    @Size(max = 1000)
    @Column(name = "payload", length = 1000, nullable = false)
    private String payload;

    @Column(name = "valid_until")
    private Instant validUntil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "unifiedPaymentTokens", allowSetters = true)
    private Merchant merchant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public UnifiedPaymentToken token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PaymentMethodType getType() {
        return type;
    }

    public UnifiedPaymentToken type(PaymentMethodType type) {
        this.type = type;
        return this;
    }

    public void setType(PaymentMethodType type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public UnifiedPaymentToken payload(String payload) {
        this.payload = payload;
        return this;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public UnifiedPaymentToken validUntil(Instant validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public UnifiedPaymentToken merchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnifiedPaymentToken)) {
            return false;
        }
        return id != null && id.equals(((UnifiedPaymentToken) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnifiedPaymentToken{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", type='" + getType() + "'" +
            ", payload='" + getPayload() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            "}";
    }
}
