package org.chatapplicationjava.service.inter;

import org.chatapplicationjava.model.Authentication;
import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.model.User;
import org.chatapplicationjava.network.Pack;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface UserService {
    public boolean register(Authentication authentication, User user);
    public boolean authenticate(Authentication authentication) ;
    public ArrayList<User> searchContacts(String search);
    public boolean invitUser(User guest);
    public boolean acceptUser(User inviter);
    public boolean refuseUser(User inviter);
    public Message sendMessage(Message message);
}
