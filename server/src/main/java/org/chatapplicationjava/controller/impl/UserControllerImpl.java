package org.chatapplicationjava.controller.impl;

import org.chatapplicationjava.controller.inter.UserController;
import org.chatapplicationjava.model.Session;
import org.chatapplicationjava.model.User;
import org.chatapplicationjava.network.Pack;
import org.chatapplicationjava.security.LocalKeyPairRSA;
import org.chatapplicationjava.security.RSA;
import org.chatapplicationjava.service.impl.SessionServiceImpl;
import org.chatapplicationjava.service.impl.UserServiceImpl;
import org.chatapplicationjava.service.inter.MessageService;
import org.chatapplicationjava.service.inter.SessionService;
import org.chatapplicationjava.service.inter.UserService;
import org.chatapplicationjava.util.ServerContext;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UserControllerImpl implements UserController {

    private UserService userService;
	private SessionService sessionService;

    public UserControllerImpl(){
        userService = new UserServiceImpl();
		sessionService = new SessionServiceImpl();
    }

    @Override
    public boolean createUser(User user) {
        return userService.createUser(user);
    }

	@Override
	public ArrayList<User> searchContacts(String search, User user) {
		return userService.searchContacts(search, user);
	}

	@Override
	public boolean invitUser(User guest, User inviter) {
		byte[] cipherObject;
		Pack invitation;
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));

		if(userService.invitUser(guest,inviter)){
			// informer le guest
			for(Session sessionGuest:sessionService.searchSession(guest.getAccount().getEmail())){
				if(sessionGuest.isReadyForCommunicate()){
					cipherObject = rsa.encrypt(new Object[]{"Invitation",new User(inviter.getFirstName(),inviter.getLastName(),inviter.getAccount())},sessionGuest.getPublicKey());
					invitation = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
					try {
						new ObjectOutputStream(sessionGuest.getClientSocket().getOutputStream()).writeObject(invitation);
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}

			}
			return true;
		}
		return false;
	}

	@Override
	public boolean acceptUser(User guest, User inviter) {
		byte[] cipherObject;
		Pack acceptation;
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));

		if(userService.acceptUser(guest,inviter)){
			System.out.println("from CONUser  " + true);

			// informer le inviter
			for(Session sessionInviter:sessionService.searchSession(inviter.getAccount().getEmail())){
				if(sessionInviter.isReadyForCommunicate()) {
					cipherObject = rsa.encrypt(new Object[]{"Acceptation", new User(guest.getFirstName(), guest.getLastName(), guest.getAccount())}, sessionInviter.getPublicKey());
					acceptation = new Pack(cipherObject, rsa.sign(cipherObject, localKeyPairRSA.getKeyPair().getPrivate()));
					try {
						new ObjectOutputStream(sessionInviter.getClientSocket().getOutputStream()).writeObject(acceptation);
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean refuseUser(User guest, User inviter) {

		byte[] cipherObject;
		Pack refuse;
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
		System.out.println("hii from userController refuse 1");


		if(userService.refuseUser(guest,inviter)){
			System.out.println("hii from userController refuse 2");

			// informer le inviter
			for(Session sessionInviter:sessionService.searchSession(inviter.getAccount().getEmail())){
				if(sessionInviter.isReadyForCommunicate()) {
					cipherObject = rsa.encrypt(new Object[]{"refuse", new User(guest.getFirstName(), guest.getLastName(), guest.getAccount())}, sessionInviter.getPublicKey());
					refuse = new Pack(cipherObject, rsa.sign(cipherObject, localKeyPairRSA.getKeyPair().getPrivate()));
					try {
						new ObjectOutputStream(sessionInviter.getClientSocket().getOutputStream()).writeObject(refuse);
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}


			}

			return true;
		}
		return false;
	}
}
