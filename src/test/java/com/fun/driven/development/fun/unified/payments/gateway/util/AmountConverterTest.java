package com.fun.driven.development.fun.unified.payments.gateway.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AmountConverterTest {

    @Test
    void fromBaseUnitToBigDecimal() {
        BigDecimal decimal = AmountConverter.fromBaseUnitToBigDecimal(1l, "EUR");
        Assertions.assertEquals("0.01", decimal.toString());

        decimal = AmountConverter.fromBaseUnitToBigDecimal(10000l, "EUR");
        Assertions.assertEquals("100.00", decimal.toString());

        decimal = AmountConverter.fromBaseUnitToBigDecimal(100021l, "EUR");
        Assertions.assertEquals("1000.21", decimal.toString());

        decimal = AmountConverter.fromBaseUnitToBigDecimal(1000016l, "EUR");
        Assertions.assertEquals("10000.16", decimal.toString());

        decimal = AmountConverter.fromBaseUnitToBigDecimal(10000005l, "EUR");
        Assertions.assertEquals("100000.05", decimal.toString());

        decimal = AmountConverter.fromBaseUnitToBigDecimal(100000099l, "EUR");
        Assertions.assertEquals("1000000.99", decimal.toString());

    }

}
