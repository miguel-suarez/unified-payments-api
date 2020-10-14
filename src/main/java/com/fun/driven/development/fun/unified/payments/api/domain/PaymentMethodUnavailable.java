package com.fun.driven.development.fun.unified.payments.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.UnavailableType;

@Entity
@Table(name = "payment_method_unavailable")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentMethodUnavailable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UnavailableType type;

    @NotNull
    @Column(name = "fun_from", nullable = false)
    private Instant from;

    @Column(name = "until")
    private Instant until;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "paymentMethodUnavailables", allowSetters = true)
    private PaymentMethod paymentMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnavailableType getType() {
        return type;
    }

    public PaymentMethodUnavailable type(UnavailableType type) {
        this.type = type;
        return this;
    }

    public void setType(UnavailableType type) {
        this.type = type;
    }

    public Instant getFrom() {
        return from;
    }

    public PaymentMethodUnavailable from(Instant from) {
        this.from = from;
        return this;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Instant getUntil() {
        return until;
    }

    public PaymentMethodUnavailable until(Instant until) {
        this.until = until;
        return this;
    }

    public void setUntil(Instant until) {
        this.until = until;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentMethodUnavailable paymentMethod(PaymentMethod paymentMethod) {
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
        if (!(o instanceof PaymentMethodUnavailable)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethodUnavailable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodUnavailable{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", from='" + getFrom() + "'" +
            ", until='" + getUntil() + "'" +
            "}";
    }
}
