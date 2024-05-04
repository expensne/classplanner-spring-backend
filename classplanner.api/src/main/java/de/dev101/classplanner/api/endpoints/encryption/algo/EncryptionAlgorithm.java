package de.dev101.classplanner.api.endpoints.encryption.algo;

import javax.crypto.SecretKey;

public interface EncryptionAlgorithm {

    SecretKey makeKey(byte[] rawKey);

    byte[] encrypt(byte[] plainText, SecretKey key, byte[] nonce) throws Exception;

    byte[] decrypt(byte[] cipherText, SecretKey key, byte[] nonce) throws Exception;
}
