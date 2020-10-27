package com.fun.driven.development.fun.unified.payments.vault;

import javax.validation.constraints.NotNull;

public class Card {

    @NotNull
    private String number;
    @NotNull
    private String expirationMonth;
    @NotNull
    private String expirationYear;

    public String getNumber() {
        return number;
    }

    public Card setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public Card setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
        return this;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public String getExpirationMMSlashYYYY() {
        return String.format("%02d", expirationMonth) + "/" + String.format("%04d", expirationYear);
    }

    public Card setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
        return this;
    }
}
