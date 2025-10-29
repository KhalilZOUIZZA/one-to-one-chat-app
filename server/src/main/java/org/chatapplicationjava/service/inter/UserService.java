package org.chatapplicationjava.service.inter;

import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.model.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserService {
	
	public boolean createUser(User user);

	public ArrayList<User> searchContacts(String search, User user);
	
	public boolean invitUser(User guest, User inviter);
	
	public boolean acceptUser(User guest, User inviter);
	
	public boolean refuseUser(User guest, User inviter);
	public User findUserWithThierContactsAndThierInviterAndThierGuest(String email);
}
