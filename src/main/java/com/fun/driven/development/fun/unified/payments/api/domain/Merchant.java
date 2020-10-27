package com.fun.driven.development.fun.unified.payments.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "merchant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Merchant implements Serializable {

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
    @Size(max = 25)
    @Column(name = "reference", length = 25, nullable = false, unique = true)
    private String reference;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @OneToMany(mappedBy = "merchant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PaymentMethodCredential> paymentMethodCredentials = new HashSet<>();

    @OneToMany(mappedBy = "merchant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UnifiedPaymentToken> unifiedPaymentTokens = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "merchant_user",
               joinColumns = @JoinColumn(name = "merchant_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "merchant_payment_method",
               joinColumns = @JoinColumn(name = "merchant_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "payment_method_id", referencedColumnName = "id"))
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

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

    public Merchant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public Merchant reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Instant getCreated() {
        return created;
    }

    public Merchant created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Set<PaymentMethodCredential> getPaymentMethodCredentials() {
        return paymentMethodCredentials;
    }

    public Merchant paymentMethodCredentials(Set<PaymentMethodCredential> paymentMethodCredentials) {
        this.paymentMethodCredentials = paymentMethodCredentials;
        return this;
    }

    public Merchant addPaymentMethodCredential(PaymentMethodCredential paymentMethodCredential) {
        this.paymentMethodCredentials.add(paymentMethodCredential);
        paymentMethodCredential.setMerchant(this);
        return this;
    }

    public Merchant removePaymentMethodCredential(PaymentMethodCredential paymentMethodCredential) {
        this.paymentMethodCredentials.remove(paymentMethodCredential);
        paymentMethodCredential.setMerchant(null);
        return this;
    }

    public void setPaymentMethodCredentials(Set<PaymentMethodCredential> paymentMethodCredentials) {
        this.paymentMethodCredentials = paymentMethodCredentials;
    }

    public Set<UnifiedPaymentToken> getUnifiedPaymentTokens() {
        return unifiedPaymentTokens;
    }

    public Merchant unifiedPaymentTokens(Set<UnifiedPaymentToken> unifiedPaymentTokens) {
        this.unifiedPaymentTokens = unifiedPaymentTokens;
        return this;
    }

    public Merchant addUnifiedPaymentToken(UnifiedPaymentToken unifiedPaymentToken) {
        this.unifiedPaymentTokens.add(unifiedPaymentToken);
        unifiedPaymentToken.setMerchant(this);
        return this;
    }

    public Merchant removeUnifiedPaymentToken(UnifiedPaymentToken unifiedPaymentToken) {
        this.unifiedPaymentTokens.remove(unifiedPaymentToken);
        unifiedPaymentToken.setMerchant(null);
        return this;
    }

    public void setUnifiedPaymentTokens(Set<UnifiedPaymentToken> unifiedPaymentTokens) {
        this.unifiedPaymentTokens = unifiedPaymentTokens;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Merchant users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Merchant addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Merchant removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public Merchant paymentMethods(Set<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
        return this;
    }

    public Merchant addPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.add(paymentMethod);
        paymentMethod.getMerchants().add(this);
        return this;
    }

    public Merchant removePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.remove(paymentMethod);
        paymentMethod.getMerchants().remove(this);
        return this;
    }

    public void setPaymentMethods(Set<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Merchant)) {
            return false;
        }
        return id != null && id.equals(((Merchant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Merchant{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", reference='" + getReference() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
