package com.camlait.global.erp.delegate.util.encryption;

/**
 * Password encryption and verification interface.
 * 
 * @author Martin Blaise Signe
 * @see StrongPasswordEncryptor
 */
public interface PasswordManager {

    /**
     * Encrypt the provided input text.
     * 
     * @param input Input that need to be encrypted
     * @return The encrypted value.
     * @see StrongPasswordEncryptor
     */
    String encrypt(final String input);

    /**
     * Verify if the provided plain password match with the encrypted one.
     * 
     * @param plainPassword password provided by the client.
     * @param encryptedPassword password retrieved from the data storage
     *            
     * @return true if the two passwords match or false otherwise.
     * @see StrongPasswordEncryptor
     */
    boolean check(final String plainPassword, final String encryptedPassword);
}
