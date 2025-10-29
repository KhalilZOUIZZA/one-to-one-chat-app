package org.chatapplicationjava.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LocalKeyPairRSA {

    private KeyPair keyPair;

    public LocalKeyPairRSA() {
        this.keyPair = null;
    }

    public void generatePairKeys(){
        try {
            KeyPairGenerator keyPairGenerator= KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            this.keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }

    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public String getPublicKeyBase64(){
        return Base64.getEncoder().encodeToString(this.keyPair.getPublic().getEncoded());
    }

    public String getPrivateKeyBase64(){
        return Base64.getEncoder().encodeToString(this.keyPair.getPrivate().getEncoded());
    }
}
