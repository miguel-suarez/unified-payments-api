package com.fun.driven.development.fun.unified.payments.api.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodCredential} entity.
 */
public class PaymentMethodCredentialDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 500)
    private String apiKey;

    private Instant validUntil;

    private Long merchantId;

    private Long paymentMethodId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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
        if (!(o instanceof PaymentMethodCredentialDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentMethodCredentialDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodCredentialDTO{" +
            "id=" + getId() +
            ", apiKey='" + getApiKey() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", merchantId=" + getMerchantId() +
            ", paymentMethodId=" + getPaymentMethodId() +
            "}";
    }
}
