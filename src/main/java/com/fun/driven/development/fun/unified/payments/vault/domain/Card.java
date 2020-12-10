package com.fun.driven.development.fun.unified.payments.vault.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;

public class Card {

    private static final Logger log = LoggerFactory.getLogger(Card.class);

    @NotNull
    private String cardNumber;
    @NotNull
    private Integer expiryMonth;
    @NotNull
    private Integer expiryYear;

    private static ObjectMapper objectMapper = new ObjectMapper();

    public String getCardNumber() {
        return cardNumber;
    }

    public Card setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public Card setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    @JsonIgnore
    public String getExpirationMMSlashYYYY() {
        return String.format("%02d", expiryMonth) + "/" + String.format("%04d", expiryYear);
    }

    public Card setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    @JsonIgnore
    public Instant lastUsableUTCInstant() {
        LocalDate firstDayMonthExpiry = LocalDate.of(expiryYear, expiryMonth, 1);
        LocalDateTime expiryDateTime = firstDayMonthExpiry.with(TemporalAdjusters.lastDayOfMonth())
                                                          .atTime(23,59,59);
        return expiryDateTime.toInstant(ZoneOffset.UTC);
    }

    @Override
    public String toString() {
        return "Card{" + "number='xxxxxxxxxxxxx'" +
            ", expirationMonth=" + expiryMonth +
            ", expirationYear=" + expiryYear + '}';
    }

    @JsonIgnore
    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Can't parse object to json", e);
            return "";
        }
    }

    @JsonIgnore
    public static Card fromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Card.class);
    }
}
