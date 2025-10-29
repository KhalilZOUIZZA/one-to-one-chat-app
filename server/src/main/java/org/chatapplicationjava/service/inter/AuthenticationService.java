package org.chatapplicationjava.service.inter;

import org.chatapplicationjava.model.Authentication;

public interface AuthenticationService {

    public boolean authenticate(Authentication authentication);
    public boolean register(Authentication authentication);
    public String getPublicKey();

}
