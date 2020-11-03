package com.fun.driven.development.fun.unified.payments.api.service.dto;

import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.PaymentMethodType;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.fun.driven.development.fun.unified.payments.api.domain.UnifiedPaymentToken} entity.
 */
@ApiModel(description = "Fun unified payment token")
public class UnifiedPaymentTokenDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 500)
    private String token;

    @NotNull
    private PaymentMethodType type;

    @NotNull
    @Size(max = 1000)
    private String payload;

    private Instant validUntil;

    private Long merchantId;

    public UnifiedPaymentTokenDTO() {
    }

    public UnifiedPaymentTokenDTO(@NotNull @Size(max = 500) String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public UnifiedPaymentTokenDTO token(String token) {
        this.token = token;
        return this;
    }

    public PaymentMethodType getType() {
        return type;
    }

    public UnifiedPaymentTokenDTO type(PaymentMethodType type) {
        this.type = type;
        return this;
    }

    public String getPayload() {
        return payload;
    }

    public UnifiedPaymentTokenDTO payload(String payload) {
        this.payload = payload;
        return this;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public UnifiedPaymentTokenDTO validUntil(Instant validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public UnifiedPaymentTokenDTO merchantId(Long merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String partialPayload() {
        Double tenPercent = getPayload().length() * 0.1;
        int limit = 1;
        if (tenPercent > 0) {
            limit = tenPercent.intValue();
        }
        return getPayload().substring(0,  limit);
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
            ", type='" + getType() + "'" +
            ", payload (partial)='" + partialPayload() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", merchantId=" + getMerchantId() +
            "}";
    }
}
