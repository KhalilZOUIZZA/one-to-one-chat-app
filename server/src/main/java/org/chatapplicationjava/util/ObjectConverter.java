package org.chatapplicationjava.util;

import java.io.*;

public class ObjectConverter {
    public static byte [] objectToByteArray(Object object)  {
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

    public static <T> T byteArrayToObject(byte[] objectBytes){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(objectBytes));
            return (T) objectInputStream.readObject() ;
        } catch (IOException | ClassNotFoundException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static byte[] concatByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
