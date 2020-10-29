package com.fun.driven.development.fun.unified.payments.vault;

import javax.validation.constraints.NotNull;

public class Card {

    @NotNull
    private String number;
    @NotNull
    private Integer expirationMonth;
    @NotNull
    private Integer expirationYear;

    public String getNumber() {
        return number;
    }

    public Card setNumber(String number) {
        this.number = number;
        return this;
    }

    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public Card setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
        return this;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public String getExpirationMMSlashYYYY() {
        return String.format("%02d", expirationMonth) + "/" + String.format("%04d", expirationYear);
    }

    public Card setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
        return this;
    }
}
