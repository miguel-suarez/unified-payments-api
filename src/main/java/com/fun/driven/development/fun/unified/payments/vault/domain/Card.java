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
    private String number;
    @NotNull
    private Integer expirationMonth;
    @NotNull
    private Integer expirationYear;

    private static ObjectMapper objectMapper = new ObjectMapper();

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

    @JsonIgnore
    public String getExpirationMMSlashYYYY() {
        return String.format("%02d", expirationMonth) + "/" + String.format("%04d", expirationYear);
    }

    public Card setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
        return this;
    }

    @JsonIgnore
    public Instant lastUsableUTCInstant() {
        LocalDate firstDayMonthExpiry = LocalDate.of(expirationYear, expirationMonth, 1);
        LocalDateTime expiryDateTime = firstDayMonthExpiry.with(TemporalAdjusters.lastDayOfMonth())
                                                          .atTime(23,59,59);
        return expiryDateTime.toInstant(ZoneOffset.UTC);
    }

    @Override
    public String toString() {
        return "Card{" + "number='xxxxxxxxxxxxx'" +
            ", expirationMonth=" + expirationMonth +
            ", expirationYear=" + expirationYear + '}';
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
