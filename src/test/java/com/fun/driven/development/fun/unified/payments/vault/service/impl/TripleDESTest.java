package com.fun.driven.development.fun.unified.payments.vault.service.impl;

import com.fun.driven.development.fun.unified.payments.api.FunUnifiedPaymentsApiApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest(classes = FunUnifiedPaymentsApiApp.class)
class TripleDESTest {

    @Autowired
    private TripleDES tripleDES;

    @Test
    public void encrypt() {
        String text = "Everything is awesome!";
        String encrypted = tripleDES.encrypt(text);
        System.out.println(encrypted);
        String decrypted = tripleDES.decrypt(encrypted);
        assertThat(decrypted).isEqualTo(text);
    }

    @Test
    public void decrypt() {
        String text = "Everything is awesome!";
        String encrypted = "f1d004cddc79d95bdf46bfa00efd564f8ae5b73f9e5d8336";
        String decrypted = tripleDES.decrypt(encrypted);
        assertThat(decrypted).isEqualTo(text);
    }
}
