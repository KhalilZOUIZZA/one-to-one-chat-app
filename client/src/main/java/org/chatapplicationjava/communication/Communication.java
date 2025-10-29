package org.chatapplicationjava.communication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Communication {
    public static  <T> T getStub(String adresse) throws MalformedURLException, NotBoundException, RemoteException {

        T stub = null;
        try {
            stub = (T) Naming.lookup(adresse);

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
            return stub;
    }
}
