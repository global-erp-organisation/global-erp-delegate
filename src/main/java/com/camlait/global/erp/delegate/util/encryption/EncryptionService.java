package com.camlait.global.erp.delegate.util.encryption;

/**
 * Password encryption and verification interface.
 * 
 * @author Martin Blaise Signe
 * @see StrongPasswordEncryptor
 */
public interface EncryptionService {

    /**
     * Encrypt the provided input
     * 
     * @param input
     * @return The encrypted value.
     * @see StrongPasswordEncryptor
     */
    String encrypt(String input);

    /**
     * Verify if the plain password match with the encrypted one.
     * 
     * @param plainPassword
     * @param encryptedPassword
     * @return true if the two passwords match or false otherwise.
     * @see StrongPasswordEncryptor
     */
    boolean check(String plainPassword, String encryptedPassword);
}
