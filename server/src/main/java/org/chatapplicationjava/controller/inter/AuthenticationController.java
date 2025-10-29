package org.chatapplicationjava.controller.inter;

import org.chatapplicationjava.model.Authentication;
import org.chatapplicationjava.network.Pack;

import java.util.UUID;

public interface AuthenticationController {
    public Object[] authenticate(Authentication authentication);
    public boolean register(Authentication authentication);
    public String getPublicKey();
}
