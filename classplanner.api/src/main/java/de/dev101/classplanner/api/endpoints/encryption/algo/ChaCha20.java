package de.dev101.classplanner.api.endpoints.encryption.algo;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ChaCha20 implements EncryptionAlgorithm {

    private final static String ALGORITHM = "ChaCha20";
    private final static int DEFAULT_COUNTER = 1;

    @Override
    public SecretKey makeKey(byte[] rawKey) {
        return new SecretKeySpec(rawKey, ALGORITHM);
    }

    @Override
    public byte[] encrypt(byte[] plainText, SecretKey key, byte[] nonce) throws Exception {
        var cipher = Cipher.getInstance(ALGORITHM);
        var param = new ChaCha20ParameterSpec(nonce, DEFAULT_COUNTER);
        cipher.init(Cipher.ENCRYPT_MODE, key, param);
        return cipher.doFinal(plainText);
    }

    @Override
    public byte[] decrypt(byte[] cipherText, SecretKey key, byte[] nonce) throws Exception {
        var cipher = Cipher.getInstance(ALGORITHM);
        var param = new ChaCha20ParameterSpec(nonce, DEFAULT_COUNTER);
        cipher.init(Cipher.DECRYPT_MODE, key, param);
        return cipher.doFinal(cipherText);
    }
}
