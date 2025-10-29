package org.chatapplicationjava.service.impl;

import org.chatapplicationjava.api.MainController;
import org.chatapplicationjava.communication.Communication;
import org.chatapplicationjava.service.inter.AuthenticationService;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public String getPublicKey() {
        try {
            MainController mainController = Communication.getStub("rmi://192.168.1.103:2017/api");
            return mainController.getPublicKey();
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
