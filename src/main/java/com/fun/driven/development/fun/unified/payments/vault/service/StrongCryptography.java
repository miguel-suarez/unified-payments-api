package com.fun.driven.development.fun.unified.payments.vault.service;

/**
 * PCI Strong Cryptography definition:
 * Cryptography based on industry-tested and accepted algorithms, along with key lengths that provide a minimum of 112-bits of effective key
 * strength and proper key-management practices. Cryptography is a method to protect data and includes both encryption (which is reversible) and
 * hashing (which is “one way”; that is, not reversible). See Hashing.
 * At the time of publication, examples of industry-tested and accepted standards and algorithms include AES (128 bits and higher), TDES/TDEA
 * (triple-length keys), RSA (2048 bits and higher), ECC (224 bits and higher), and DSA/D-H (2048/224 bits and higher). See the current version of
 * NIST Special Publication 800-57 Part 1 (http://csrc.nist.gov/publications/) for more guidance on cryptographic key strengths and algorithms.
 */
public interface StrongCryptography {

    String encrypt(String unencryptedString);
    String decrypt(String encryptedString);
}
