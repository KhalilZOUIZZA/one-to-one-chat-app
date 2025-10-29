package org.chatapplicationjava.service.impl;

import org.chatapplicationjava.dal.impl.AccountDaoImpl;
import org.chatapplicationjava.dal.impl.DiscussionDaoImpl;
import org.chatapplicationjava.dal.impl.MessageDaoImpl;
import org.chatapplicationjava.dal.impl.UserDaoImpl;
import org.chatapplicationjava.dal.inter.DiscussionDao;
import org.chatapplicationjava.dal.inter.MessageDao;
import org.chatapplicationjava.dal.inter.UserDao;
import org.chatapplicationjava.model.*;
import org.chatapplicationjava.service.inter.UserService;

import java.util.ArrayList;
import java.util.Date;

public class UserServiceImpl implements UserService {

	@Override
	public boolean createUser(User user) {
		UserDaoImpl userDao = new UserDaoImpl();
		AccountDaoImpl accountDao = new AccountDaoImpl();
		user.getAccount().setCreationDate(new Date());
		return accountDao.saveCompte(user.getAccount()) && userDao.saveUser(user);
	}

	@Override
	public ArrayList<User> searchContacts(String search, User user) {
		UserDao userDao = new UserDaoImpl();
		
		String keySearch1,keySearch2,keySearch3;
		
	    search = search.replaceAll("\\s+", " ");
        search = search.trim();

	    String[] parts = search.split(" ");

	    keySearch1 = (parts.length > 0) ? parts[0] : "";
	    keySearch2 = (parts.length > 1) ? parts[1] : "";
	    keySearch3 = (parts.length > 2) ? parts[2] : "";
		
		return userDao.findUserByNameOrLastName(keySearch1, keySearch2, keySearch3, user);
	}

	@Override
	public boolean invitUser(User guest, User inviter) {
		UserDao userDao = new UserDaoImpl();
		return userDao.insertInvitation(guest, inviter);
	}

	@Override
	public boolean acceptUser(User guest, User inviter) {
		UserDao userDao = new UserDaoImpl();
		DiscussionDao discussionDao = new DiscussionDaoImpl();
		MessageDao messageDao = new MessageDaoImpl();
		boolean re1 = userDao.acceptInvitaion(guest, inviter);
		boolean re2 = discussionDao.saveDiscussion(new Discussion(guest,inviter));
		boolean re3 = (messageDao.saveMessage(new Message(inviter, new Date(), new Discussion(guest, inviter))) != null);
		System.out.println("from serviceUser  " + re1 + re2 + re3);
		return (re1 && re2 && re3 );
	}

	@Override
	public boolean refuseUser(User guest, User inviter) {
		UserDao userDao = new UserDaoImpl();
		return userDao.refuseInvitaion(guest, inviter);
	}

	@Override
	public User findUserWithThierContactsAndThierInviterAndThierGuest(String email) {
		UserDao userDao = new UserDaoImpl();
		User user = userDao.findUserByEmail(email);
		if (user == null)
			return null;
		user.setContacts(userDao.findContactsUserByEmail(user));
		user.setInviters(userDao.findInviterUserByEmail(user));
		user.setGuests(userDao.findGuestUserByEmail(user));
		return user;
	}
}
