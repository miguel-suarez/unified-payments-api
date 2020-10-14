package com.fun.driven.development.fun.unified.payments.api.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fun.driven.development.fun.unified.payments.api.domain.Currency} entity.
 */
public class CurrencyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 3)
    private String isoCode;

    @NotNull
    private Integer isoNumericCode;

    @NotNull
    private Integer defaultFractionDigits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public Integer getIsoNumericCode() {
        return isoNumericCode;
    }

    public void setIsoNumericCode(Integer isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
    }

    public Integer getDefaultFractionDigits() {
        return defaultFractionDigits;
    }

    public void setDefaultFractionDigits(Integer defaultFractionDigits) {
        this.defaultFractionDigits = defaultFractionDigits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyDTO)) {
            return false;
        }

        return id != null && id.equals(((CurrencyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyDTO{" +
            "id=" + getId() +
            ", isoCode='" + getIsoCode() + "'" +
            ", isoNumericCode=" + getIsoNumericCode() +
            ", defaultFractionDigits=" + getDefaultFractionDigits() +
            "}";
    }
}
