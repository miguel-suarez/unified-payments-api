package com.fun.driven.development.fun.unified.payments.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "iso_code", length = 3, nullable = false, unique = true)
    private String isoCode;

    @NotNull
    @Column(name = "iso_numeric_code", nullable = false, unique = true)
    private Integer isoNumericCode;

    @NotNull
    @Column(name = "default_fraction_digits", nullable = false)
    private Integer defaultFractionDigits;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public Currency isoCode(String isoCode) {
        this.isoCode = isoCode;
        return this;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public Integer getIsoNumericCode() {
        return isoNumericCode;
    }

    public Currency isoNumericCode(Integer isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
        return this;
    }

    public void setIsoNumericCode(Integer isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
    }

    public Integer getDefaultFractionDigits() {
        return defaultFractionDigits;
    }

    public Currency defaultFractionDigits(Integer defaultFractionDigits) {
        this.defaultFractionDigits = defaultFractionDigits;
        return this;
    }

    public void setDefaultFractionDigits(Integer defaultFractionDigits) {
        this.defaultFractionDigits = defaultFractionDigits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currency)) {
            return false;
        }
        return id != null && id.equals(((Currency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", isoCode='" + getIsoCode() + "'" +
            ", isoNumericCode=" + getIsoNumericCode() +
            ", defaultFractionDigits=" + getDefaultFractionDigits() +
            "}";
    }
}
