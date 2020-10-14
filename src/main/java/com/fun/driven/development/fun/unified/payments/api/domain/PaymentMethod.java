package com.fun.driven.development.fun.unified.payments.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.PaymentMethodType;

@Entity
@Table(name = "payment_method")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PaymentMethodType type;

    @NotNull
    @Column(name = "tokenizable", nullable = false)
    private Boolean tokenizable;

    @NotNull
    @Size(max = 1000)
    @Column(name = "url", length = 1000, nullable = false)
    private String url;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "paymentMethod")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PaymentMethodUnavailable> paymentMethodUnavailables = new HashSet<>();

    @OneToMany(mappedBy = "paymentMethod")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PaymentMethodCredential> paymentMethodCredentials = new HashSet<>();

    @ManyToMany(mappedBy = "paymentMethods")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Merchant> merchants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PaymentMethod name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentMethodType getType() {
        return type;
    }

    public PaymentMethod type(PaymentMethodType type) {
        this.type = type;
        return this;
    }

    public void setType(PaymentMethodType type) {
        this.type = type;
    }

    public Boolean isTokenizable() {
        return tokenizable;
    }

    public PaymentMethod tokenizable(Boolean tokenizable) {
        this.tokenizable = tokenizable;
        return this;
    }

    public void setTokenizable(Boolean tokenizable) {
        this.tokenizable = tokenizable;
    }

    public String getUrl() {
        return url;
    }

    public PaymentMethod url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isActive() {
        return active;
    }

    public PaymentMethod active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<PaymentMethodUnavailable> getPaymentMethodUnavailables() {
        return paymentMethodUnavailables;
    }

    public PaymentMethod paymentMethodUnavailables(Set<PaymentMethodUnavailable> paymentMethodUnavailables) {
        this.paymentMethodUnavailables = paymentMethodUnavailables;
        return this;
    }

    public PaymentMethod addPaymentMethodUnavailable(PaymentMethodUnavailable paymentMethodUnavailable) {
        this.paymentMethodUnavailables.add(paymentMethodUnavailable);
        paymentMethodUnavailable.setPaymentMethod(this);
        return this;
    }

    public PaymentMethod removePaymentMethodUnavailable(PaymentMethodUnavailable paymentMethodUnavailable) {
        this.paymentMethodUnavailables.remove(paymentMethodUnavailable);
        paymentMethodUnavailable.setPaymentMethod(null);
        return this;
    }

    public void setPaymentMethodUnavailables(Set<PaymentMethodUnavailable> paymentMethodUnavailables) {
        this.paymentMethodUnavailables = paymentMethodUnavailables;
    }

    public Set<PaymentMethodCredential> getPaymentMethodCredentials() {
        return paymentMethodCredentials;
    }

    public PaymentMethod paymentMethodCredentials(Set<PaymentMethodCredential> paymentMethodCredentials) {
        this.paymentMethodCredentials = paymentMethodCredentials;
        return this;
    }

    public PaymentMethod addPaymentMethodCredential(PaymentMethodCredential paymentMethodCredential) {
        this.paymentMethodCredentials.add(paymentMethodCredential);
        paymentMethodCredential.setPaymentMethod(this);
        return this;
    }

    public PaymentMethod removePaymentMethodCredential(PaymentMethodCredential paymentMethodCredential) {
        this.paymentMethodCredentials.remove(paymentMethodCredential);
        paymentMethodCredential.setPaymentMethod(null);
        return this;
    }

    public void setPaymentMethodCredentials(Set<PaymentMethodCredential> paymentMethodCredentials) {
        this.paymentMethodCredentials = paymentMethodCredentials;
    }

    public Set<Merchant> getMerchants() {
        return merchants;
    }

    public PaymentMethod merchants(Set<Merchant> merchants) {
        this.merchants = merchants;
        return this;
    }

    public PaymentMethod addMerchant(Merchant merchant) {
        this.merchants.add(merchant);
        merchant.getPaymentMethods().add(this);
        return this;
    }

    public PaymentMethod removeMerchant(Merchant merchant) {
        this.merchants.remove(merchant);
        merchant.getPaymentMethods().remove(this);
        return this;
    }

    public void setMerchants(Set<Merchant> merchants) {
        this.merchants = merchants;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethod)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethod) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethod{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", tokenizable='" + isTokenizable() + "'" +
            ", url='" + getUrl() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
