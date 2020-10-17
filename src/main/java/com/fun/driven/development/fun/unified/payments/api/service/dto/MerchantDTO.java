package com.fun.driven.development.fun.unified.payments.api.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.fun.driven.development.fun.unified.payments.api.domain.Merchant} entity.
 */
public class MerchantDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 250)
    private String name;

    @NotNull
    @Size(max = 25)
    private String reference;

    @NotNull
    private Instant created;

    private Set<UserDTO> users = new HashSet<>();
    private Set<PaymentMethodDTO> paymentMethods = new HashSet<>();

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public Set<PaymentMethodDTO> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(Set<PaymentMethodDTO> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MerchantDTO)) {
            return false;
        }

        return id != null && id.equals(((MerchantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MerchantDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", reference='" + getReference() + "'" +
            ", created='" + getCreated() + "'" +
            ", users='" + getUsers() + "'" +
            ", paymentMethods='" + getPaymentMethods() + "'" +
            "}";
    }
}
