package org.chatapplicationjava.service.impl;

import org.chatapplicationjava.dal.impl.AuthenticationDaoImpl;
import org.chatapplicationjava.dal.inter.AuthenticationDao;
import org.chatapplicationjava.model.Authentication;
import org.chatapplicationjava.security.LocalKeyPairRSA;
import org.chatapplicationjava.security.SHA256;
import org.chatapplicationjava.service.inter.AuthenticationService;
import org.chatapplicationjava.util.ServerContext;

public class AuthenticationServiceImpl implements AuthenticationService {


    @Override
    public boolean authenticate(Authentication authentication) {
        AuthenticationDao authenticationDao = new AuthenticationDaoImpl();

        authentication.setPassword((new SHA256()).hash(authentication.getPassword()));
        return authenticationDao.findByPassword(authentication) != null;
    }

    @Override
    public boolean register(Authentication authentication) {
        AuthenticationDao authenticationDao = new AuthenticationDaoImpl();

        authentication.setPassword((new SHA256()).hash(authentication.getPassword()));
        return authenticationDao.saveAuthentication(authentication);
    }

    @Override
    public String getPublicKey() {
        return ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA")).getPublicKeyBase64();
    }
}
