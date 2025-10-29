package org.chatapplicationjava.dal.inter;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.ArrayList;
import org.chatapplicationjava.model.User;

public interface UserDao {

	public boolean saveUser(User user);
	public boolean insertInvitation(User invite, User invitant);
	public boolean acceptInvitaion(User invite,User invitant);
	public boolean refuseInvitaion(User invite,User invitant);
	//public boolean blockContact(User blocker,User blocked);
	public ArrayList<User> findUserByNameOrLastName(String keySearch1, String keySearch2, String keySearch3, User user);
	public User findUserByEmail(String email);
	public ArrayList<User> findContactsUserByEmail(User user);
	public ArrayList<User> findGuestUserByEmail(User user);
	public ArrayList<User> findInviterUserByEmail(User user);

}
