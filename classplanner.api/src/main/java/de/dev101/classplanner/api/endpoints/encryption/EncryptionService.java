package de.dev101.classplanner.api.endpoints.encryption;

import java.security.SecureRandom;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import de.dev101.classplanner.api.endpoints.encryption.algo.ChaCha20;
import de.dev101.classplanner.api.endpoints.encryption.algo.EncryptionAlgorithm;

@Service
public class EncryptionService {

    private final EncryptionAlgorithm encryptionAlgorithm;
    private final SecretKey key;

    public EncryptionService(EncryptionProperties props) {
        encryptionAlgorithm = new ChaCha20();
        key = encryptionAlgorithm.makeKey(props.getSecret().getBytes());
    }

    public byte[] encrypt(byte[] plainText, byte[] nonce) {
        try {
            return encryptionAlgorithm.encrypt(plainText, key, nonce);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting data", e);
        }
    }

    public byte[] decrypt(byte[] cipherText, byte[] nonce) {
        try {
            return encryptionAlgorithm.decrypt(cipherText, key, nonce);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting data", e);
        }
    }

    public byte[] generateNonce() {
        byte[] nonce = new byte[12];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
}