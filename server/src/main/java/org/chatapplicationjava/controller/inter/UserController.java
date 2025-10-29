package org.chatapplicationjava.controller.inter;

import org.chatapplicationjava.model.User;

import java.util.ArrayList;

public interface UserController {
	
    public boolean createUser(User user);
    
    public ArrayList<User> searchContacts(String search, User user);
	
	public boolean invitUser(User guest, User inviter);
	
	public boolean acceptUser(User guest, User inviter);
	
	public boolean refuseUser(User guest, User inviter);

}
