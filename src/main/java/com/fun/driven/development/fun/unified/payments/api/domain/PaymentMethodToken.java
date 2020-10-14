package com.fun.driven.development.fun.unified.payments.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * Payment Method propietary token
 */
@Entity
@Table(name = "payment_method_token")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentMethodToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "token", length = 500, nullable = false, unique = true)
    private String token;

    @Column(name = "valid_until")
    private Instant validUntil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "paymentMethodTokens", allowSetters = true)
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

    public PaymentMethodToken token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public PaymentMethodToken validUntil(Instant validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public PaymentMethodToken merchant(Merchant merchant) {
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
        if (!(o instanceof PaymentMethodToken)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethodToken) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodToken{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            "}";
    }
}
