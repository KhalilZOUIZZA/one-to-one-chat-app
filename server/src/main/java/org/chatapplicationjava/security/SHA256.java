package org.chatapplicationjava.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SHA256 {


    public String hash(Object object){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] cipherObject = digest.digest(objectToByteArray(object));
            return Base64.getEncoder().encodeToString(cipherObject);
        } catch (NoSuchAlgorithmException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }

    }

    private byte [] objectToByteArray(Object object)  {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }
}
