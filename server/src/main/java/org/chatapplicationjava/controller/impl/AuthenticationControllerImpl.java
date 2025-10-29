package org.chatapplicationjava.controller.impl;

import org.chatapplicationjava.controller.inter.AuthenticationController;
import org.chatapplicationjava.model.Authentication;
import org.chatapplicationjava.model.Session;
import org.chatapplicationjava.model.User;
import org.chatapplicationjava.security.RSA;
import org.chatapplicationjava.service.impl.AuthenticationServiceImpl;
import org.chatapplicationjava.service.impl.SessionServiceImpl;
import org.chatapplicationjava.service.impl.UserServiceImpl;
import org.chatapplicationjava.service.inter.AuthenticationService;
import org.chatapplicationjava.service.inter.SessionService;
import org.chatapplicationjava.service.inter.UserService;
import org.chatapplicationjava.util.ServerContext;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.UUID;

public class AuthenticationControllerImpl implements AuthenticationController {

    // Attributs
    private  AuthenticationService authenticationService;
    private SessionService sessionService;
    private UserService userService;

    //Constructors
    public AuthenticationControllerImpl() {
        this.authenticationService = new AuthenticationServiceImpl();
        this.sessionService = new SessionServiceImpl();
        this.userService = new UserServiceImpl();
    }

    // Methods
    @Override
    public Object[] authenticate(Authentication authentication) {
        User user;
        UUID uuid;
        Session session;
        PublicKey publicKey;
        Object[] uuidAndUser = new Object[2];
        if(this.authenticationService.authenticate(authentication)){
            uuid = this.sessionService.generateIdSession();
            session = new Session(uuid);
            //session.setInLoadData(true); //new Session(uuid,user,publicKey, true)
            user = userService.findUserWithThierContactsAndThierInviterAndThierGuest(authentication.getEmail());
            session.setUser(user);
            sessionService.getSessions().put(uuid, session);
            publicKey = (new RSA()).decodeBase64PublicKeyToPublicKey(authentication.getPublicKeyBase64());
            session.setPublicKey(publicKey);
            uuidAndUser[0] = uuid;
            uuidAndUser[1] = user;
            // le probleme de inload est qu'on aura besoin ou non
            return uuidAndUser;
        }
        else{
            return null;
        }
    }

    @Override
    public boolean register(Authentication authentication) {
        return authenticationService.register(authentication);
    }

    @Override
    public String getPublicKey() {
        return authenticationService.getPublicKey();
    }
}
