package com.fun.driven.development.fun.unified.payments.api.service.dto;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodToken} entity.
 */
@ApiModel(description = "Payment Method propietary token")
public class PaymentMethodTokenDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 500)
    private String token;

    private Instant validUntil;

    private Long merchantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethodTokenDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentMethodTokenDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodTokenDTO{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", merchantId=" + getMerchantId() +
            "}";
    }
}
