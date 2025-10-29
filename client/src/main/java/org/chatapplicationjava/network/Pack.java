package org.chatapplicationjava.network;

import java.io.Serializable;

public class Pack implements Serializable {

    private byte[] cipherObject;
    private byte[] signature;

    public Pack(byte[] cipherObject, byte[] signature) {
        this.cipherObject = cipherObject;
        this.signature = signature;
    }

    public byte[] getCipherObject() {
        return cipherObject;
    }

    public void setCipherObject(byte[] cipherObject) {
        this.cipherObject = cipherObject;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
