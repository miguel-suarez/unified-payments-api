package com.fun.driven.development.fun.unified.payments.api.service.dto;

import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.UnavailableType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodUnavailable} entity.
 */
public class PaymentMethodUnavailableDTO implements Serializable {

    private Long id;

    @NotNull
    private UnavailableType type;

    @NotNull
    private Instant from;

    private Instant until;

    private Long paymentMethodId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnavailableType getType() {
        return type;
    }

    public void setType(UnavailableType type) {
        this.type = type;
    }

    public Instant getFrom() {
        return from;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Instant getUntil() {
        return until;
    }

    public void setUntil(Instant until) {
        this.until = until;
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
        if (!(o instanceof PaymentMethodUnavailableDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentMethodUnavailableDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodUnavailableDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", from='" + getFrom() + "'" +
            ", until='" + getUntil() + "'" +
            ", paymentMethodId=" + getPaymentMethodId() +
            "}";
    }
}
