package com.camlait.global.erp.delegate.util.encryption;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Default implementation of the encryption service.
 * 
 * @author Martin Blaise Signe
 */
@Component
public class DefaultPasswordManager implements PasswordManager {

    private final StrongPasswordEncryptor strongEncryptor;

    @Autowired
    public DefaultPasswordManager(final StrongPasswordEncryptor strongEncryptor) {
        this.strongEncryptor = strongEncryptor;
    }

    @Override
    public String encrypt(final String input) {
        return strongEncryptor.encryptPassword(input);
    }

    @Override
    public boolean check(final String plainPassword, final String encryptedPassword) {
        return strongEncryptor.checkPassword(plainPassword, encryptedPassword);
    }
}
