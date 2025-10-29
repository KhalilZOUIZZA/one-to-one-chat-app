package org.chatapplicationjava.security;

import org.chatapplicationjava.util.ObjectConverter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    public byte[] encrypt(Object object, PublicKey publicKey){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            byte[] data = (ObjectConverter.objectToByteArray(object));

            int blockSize = 245;
            int inputLength = data.length;
            int offset = 0;

            byte[] encryptedObject = new byte[0];

            while (offset < inputLength) {
                int blockLength = Math.min(inputLength - offset, blockSize);
                byte[] block = new byte[blockLength];
                System.arraycopy(data, offset, block, 0, blockLength);

                byte[] encryptedBlock = cipher.doFinal(block);

                // Concaténer les blocs chiffrés
                encryptedObject = ObjectConverter.concatByteArrays(encryptedObject, encryptedBlock);

                offset += blockSize;
            }

            return encryptedObject;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }


    public <T> T decrypt(byte[] cipherObject, PrivateKey privateKey){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,privateKey);

            int blockSize = 256;
            int inputLength = cipherObject.length;
            int offset = 0;

            byte[] decryptedObject = new byte[0];

            while (offset < inputLength) {
                int blockLength = Math.min(inputLength - offset, blockSize );
                byte[] block = new byte[blockLength];
                System.arraycopy(cipherObject, offset, block, 0, blockLength);

                byte[] decryptedBlock = cipher.doFinal(block);

                // Concaténer les blocs déchiffrés
                decryptedObject = ObjectConverter.concatByteArrays(decryptedObject, decryptedBlock);

                offset += blockSize; // Encodage Base64
            }
        return ObjectConverter.<T>byteArrayToObject(decryptedObject);

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    public byte[] sign(byte[] cipherObject, PrivateKey privateKey){
        try{
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey, new SecureRandom());
            signature.update(cipherObject);
            return signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean checkSignature( byte[] signature, byte[] cipherObject, PublicKey publicKey){
        try {
            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(cipherObject);
            return verifier.verify(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String encodePublicKeyToBase64(PublicKey publicKey){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public PublicKey decodeBase64PublicKeyToPublicKey(String base64PublicKey){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] bytesPublicKey =  Base64.getDecoder().decode(base64PublicKey);
            return (PublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(bytesPublicKey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }



}
