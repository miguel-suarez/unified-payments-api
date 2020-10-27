package com.fun.driven.development.fun.unified.payments.gateway.util;

import java.math.BigDecimal;
import java.util.Currency;

public class AmountConverter {

    public static BigDecimal fromBaseUnitToBigDecimal(Long amountInCents, String currencyIsoCode) {
        Currency currency = Currency.getAvailableCurrencies()
                                    .stream()
                                    .filter(c -> c.getCurrencyCode().equals(currencyIsoCode))
                                    .findFirst().orElseThrow(IllegalArgumentException::new);
        int fractionDigits = currency.getDefaultFractionDigits();
        Double decimalAmount = amountInCents / Math.pow(10, fractionDigits);
        return BigDecimal.valueOf(decimalAmount).setScale(fractionDigits);
    }
}
