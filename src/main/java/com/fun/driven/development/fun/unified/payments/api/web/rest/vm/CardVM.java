package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@ApiModel(description = "Card for which the token will be generated")
@JsonRootName(value = "Card")
public class CardVM {

    @JsonIgnore
    private static LuhnCheckDigit luhnCheck = new LuhnCheckDigit();

    @JsonProperty("cardNumber")
    @ApiModelProperty(example = "370000000000002", required = true)
    @Size(min=10,max=20)
    @NotNull
    private String cardNumber;

    @JsonProperty("expiryMonth")
    @ApiModelProperty(example = "3", required = true)
    @NotNull
    @Min(1) @Max(12)
    private Integer expiryMonth;

    @JsonProperty("expiryYear")
    @ApiModelProperty(example = "2030", required = true)
    @NotNull
    @Min(2020) @Max(2100)
    private Integer expiryYear;


    public String getCardNumber() {
        return cardNumber;
    }

    public CardVM cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public CardVM expiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public CardVM expiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    @JsonIgnore
    public boolean hasInvalidCardNumber() {
        return ! luhnCheck.isValid(cardNumber);
    }

    @JsonIgnore
    public boolean isExpired() {
        LocalDate firstDayMonthExpiry = LocalDate.of(expiryYear, expiryMonth, 1);
        LocalDate expiryDate = firstDayMonthExpiry.with(TemporalAdjusters.lastDayOfMonth());
        return expiryDate.isBefore(LocalDate.now());
    }
}
