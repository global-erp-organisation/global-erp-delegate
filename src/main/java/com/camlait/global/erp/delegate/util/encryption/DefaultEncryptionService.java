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
public class DefaultEncryptionService implements EncryptionService {

    private final StrongPasswordEncryptor strongEncryptor;

    @Autowired
    public DefaultEncryptionService() {
        this.strongEncryptor = new StrongPasswordEncryptor();
    }

    @Override
    public String encrypt(String input) {
        return strongEncryptor.encryptPassword(input);
    }

    @Override
    public boolean check(String plainPassword, String encryptedPassword) {
        return strongEncryptor.checkPassword(plainPassword, encryptedPassword);
    }
}
