package com.fun.driven.development.fun.unified.payments.api.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.fun.driven.development.fun.unified.payments.api.domain.UnifiedPaymentToken} entity.
 */
public class UnifiedPaymentTokenDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 500)
    private String token;

    @Pattern(regexp="/^\\d{4}-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d(\\.\\d+)?(([+-]\\d\\d:\\d\\d)|Z)?$/i")
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

    public UnifiedPaymentTokenDTO token(String token) {
        this.token = token;
        return this;
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
        if (!(o instanceof UnifiedPaymentTokenDTO)) {
            return false;
        }

        return id != null && id.equals(((UnifiedPaymentTokenDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnifiedPaymentTokenDTO{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", merchantId=" + getMerchantId() +
            "}";
    }
}
