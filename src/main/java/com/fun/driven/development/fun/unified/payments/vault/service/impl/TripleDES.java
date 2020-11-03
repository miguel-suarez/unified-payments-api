package com.fun.driven.development.fun.unified.payments.vault.service.impl;

import com.fun.driven.development.fun.unified.payments.vault.service.StrongCryptography;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 * Follows cardholder store requirements PCI DSS v3.2.1, May 2018
 * Follows National Institute of Standards and Technology 800-131A Rev. 2
 * -> TDES (triple-length keys) / AES (128 bits and higher)
 * Recommendation for Block Cipher Modes of Operation: The CCM Mode for Authentication and Confidentiality
 *
 * DISCLAIMER: For a real life PCI compliant application the lifecycle and storage of the master key has
 * higher security standards, but those are out of the scope of this project
 */
@Service
public class TripleDES implements StrongCryptography {

    private static final Logger log = LoggerFactory.getLogger(TripleDES.class);
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    //public static final String DESEDE_ENCRYPTION_SCHEME = "DESede/CBC/PKCS5Padding";

    @Value("${application.tdes-key-material}")
    private String keyMaterial;
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    private SecretKey key;

    @PostConstruct
    public void init() throws GeneralSecurityException {
        if (keyMaterial == null) {
            throw new IllegalArgumentException("Key material not defined for current environment");
        }
        ks = new DESedeKeySpec(Hex.decode(keyMaterial));
        skf = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);
        cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
        key = skf.generateSecret(ks);
    }

    @Override
    public String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new SecureRandom());
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Hex.encode(encryptedText));
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            log.error("Error encrypting: ", e);
        }
        return encryptedString;
    }

    @Override
    public String decrypt(String encryptedString) {
        String decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new SecureRandom());
            byte[] encryptedText = Hex.decode(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (GeneralSecurityException e) {
            log.error("Error decrypting: ", e);;
        }
        return decryptedText;
    }

    public static void main(String args []) throws GeneralSecurityException {
        TripleDES td= new TripleDES();

        String target="This is a test 3des Saturday project";
        String encrypted=td.encrypt(target);
        String decrypted=td.decrypt(encrypted);

        System.out.println("String To Encrypt:"+ target);
        System.out.println("Encrypted String:" + encrypted);
        System.out.println("Decrypted String:" + decrypted);

    }

}
