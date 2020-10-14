package com.fun.driven.development.fun.unified.payments.api.service.dto;

import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.PaymentMethodType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethod} entity.
 */
public class PaymentMethodDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 250)
    private String name;

    @NotNull
    private PaymentMethodType type;

    @NotNull
    private Boolean tokenizable;

    @NotNull
    @Size(max = 1000)
    private String url;

    @NotNull
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentMethodType getType() {
        return type;
    }

    public void setType(PaymentMethodType type) {
        this.type = type;
    }

    public Boolean isTokenizable() {
        return tokenizable;
    }

    public void setTokenizable(Boolean tokenizable) {
        this.tokenizable = tokenizable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethodDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentMethodDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", tokenizable='" + isTokenizable() + "'" +
            ", url='" + getUrl() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
