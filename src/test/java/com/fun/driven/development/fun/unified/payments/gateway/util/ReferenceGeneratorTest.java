package com.fun.driven.development.fun.unified.payments.gateway.util;

import com.fun.driven.development.fun.unified.payments.api.FunUnifiedPaymentsApiApp;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest(classes = FunUnifiedPaymentsApiApp.class)
class ReferenceGeneratorTest {

    @Autowired
    private ReferenceGenerator generator;
    private LuhnCheckDigit luhn = new LuhnCheckDigit();

    @Test
    public void testUniqueReferencesGeneration() {
        List<String> references = new ArrayList<>();

        for (int i = 1; i<= 15000; i++) {
            String reference = generator.generate();
            Assertions.assertFalse(references.contains(reference), "Found duplicated reference");
            references.add(reference);
        }
    }

    @Test
    public void testValidReferencesGeneration() {
        long timeBeforeTest = Instant.now().getEpochSecond();

        for (int i = 1; i<= 15000; i++) {
            String reference = generator.generate();
            String invalidMsg = validate(reference, timeBeforeTest);
            Assertions.assertNull(invalidMsg, invalidMsg);
        }
    }

    private String validate(String input, long timeBeforeTest)  {
        // Validate length
        if (input == null || input.length() != 20) {
            return "Reference must have a length of 20 characters " + input;
        }

        // Validate numeric reference
        try {
            Long.parseLong(input.substring(0,11));
            Long.parseLong(input.substring(11));
        } catch(NumberFormatException e) {
            return "Reference must be numeric " + input;
        }

        // Validate Luhn checksum
        if (! luhn.isValid(input)) {
            return "Reference checksum is invalid " + input;
        }

        // Validate version & system id
        if (! input.startsWith("19999")) {
            return "Reference version or system id invalid " + input;
        }

        long timeWithinReference = Long.valueOf(input.substring(5, 15));

        if (timeWithinReference < timeBeforeTest ||
            timeWithinReference > Instant.now().getEpochSecond()) {
            return "Reference contains invalid EPOCH timestamp " + input;
        }

        return null;
    }
}
