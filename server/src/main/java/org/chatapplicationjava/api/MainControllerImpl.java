package org.chatapplicationjava.api;

import org.chatapplicationjava.controller.impl.AuthenticationControllerImpl;
import org.chatapplicationjava.controller.impl.MessageControllerImpl;
import org.chatapplicationjava.controller.impl.SessionControllerImpl;
import org.chatapplicationjava.controller.impl.UserControllerImpl;
import org.chatapplicationjava.controller.inter.AuthenticationController;
import org.chatapplicationjava.controller.inter.MessageController;
import org.chatapplicationjava.controller.inter.SessionController;
import org.chatapplicationjava.controller.inter.UserController;
import org.chatapplicationjava.model.Authentication;
import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.model.Session;
import org.chatapplicationjava.model.User;
import org.chatapplicationjava.network.Pack;
import org.chatapplicationjava.security.LocalKeyPairRSA;
import org.chatapplicationjava.security.RSA;
import org.chatapplicationjava.util.ObjectConverter;
import org.chatapplicationjava.util.ServerContext;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class MainControllerImpl extends UnicastRemoteObject implements MainController {
	private final AuthenticationController authenticationController;
	private final MessageController messageController;
	private final SessionController sessionController;
	private final UserController userController;

    public MainControllerImpl() throws RemoteException {
        super();
		this.authenticationController = new AuthenticationControllerImpl();
		this.messageController = new MessageControllerImpl();
		this.sessionController = new SessionControllerImpl();
		this.userController = new UserControllerImpl();
    }

	@Override
	public Pack searchContacts(Pack pack) throws RemoteException {
		// il faut envoye uuid search user
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
		Object[] objects = rsa.decrypt(pack.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
		UUID uuid = (UUID) objects[0];
		Session session = this.sessionController.getSession(uuid);
		Pack packUsers;

		if(session !=null && rsa.checkSignature(pack.getSignature(), pack.getCipherObject(), session.getPublicKey())){
			String search = (String) objects[1];
			User user = (User) objects[2];
			ArrayList<User> users = this.userController.searchContacts(search, user);
			byte[] cipherObject =  rsa.encrypt(users,session.getPublicKey());
			byte[] signature = rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate());
			packUsers = new Pack(cipherObject,signature);
			return packUsers;
		}
		return null;
	}

	@Override
	public Pack invitUser(Pack pack) throws RemoteException {
		System.out.println("hii from maincontroller invit 3");
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
		Pack packInvitation;
		byte[] cipherObject;
		Object[] objects = rsa.decrypt(pack.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
		UUID uuid = (UUID) objects[0];
		Session session = this.sessionController.getSession(uuid);
		User guest;
		User inviter;


		if(session !=null && rsa.checkSignature(pack.getSignature(), pack.getCipherObject(), session.getPublicKey())){
			guest = (User) objects[1];
			inviter = (User) objects[2];
			Boolean result = false;
			result = this.userController.invitUser(guest,inviter);
			cipherObject = rsa.encrypt(result,session.getPublicKey());
			packInvitation = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
			return packInvitation;
		}
		return null;
	}

	@Override
	public Pack acceptUser(Pack pack) throws RemoteException {
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
		Pack packAcceptation;
		byte[] cipherObject;
		Object[] objects = rsa.decrypt(pack.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
		UUID uuid = (UUID) objects[0];
		Session session = this.sessionController.getSession(uuid);
		User guest;
		User inviter;

		if(session !=null && rsa.checkSignature(pack.getSignature(), pack.getCipherObject(), session.getPublicKey())){
			guest = (User) objects[1];
			inviter = (User) objects[2];
			Boolean result =false;
			result = this.userController.acceptUser(guest,inviter);
			cipherObject = rsa.encrypt(result,session.getPublicKey());
			packAcceptation = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
			return packAcceptation;
		}
		return null;
	}

	@Override
	public Pack refuseUser(Pack pack) throws RemoteException {
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
		Pack packRefuse;
		byte[] cipherObject;
		Object[] objects = rsa.decrypt(pack.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
		UUID uuid = (UUID) objects[0];
		Session session = this.sessionController.getSession(uuid);
		User guest;
		User inviter;
		if(session !=null && rsa.checkSignature(pack.getSignature(), pack.getCipherObject(), session.getPublicKey())){
			guest = (User) objects[1];

			inviter = (User) objects[2];
			System.out.println("hii from maincontroller refuse 4");

			Boolean result = false;
			System.out.println("hii from maincontroller refuse 5");

			result = this.userController.refuseUser(guest,inviter);
			System.out.println("hii from maincontroller refuse 6");

			cipherObject = rsa.encrypt(result,session.getPublicKey());
			System.out.println("hii from maincontroller refuse 7");

			packRefuse = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
			System.out.println("hii from maincontroller refuse 8");
			return packRefuse;
		}
		System.out.println("hii from maincontroller refuse 9");

		return null;
	}



	@Override
	public Pack authenticate(Pack pack) {
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
		Authentication authentication = rsa.decrypt(pack.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
		Object[] objects = authenticationController.authenticate(authentication);
		Session session = sessionController.getSessions().get((UUID) objects[0]);
		byte[] cipherText = rsa.encrypt(objects ,session.getPublicKey());
		//session.setInLoadData(true);
		return new Pack(cipherText, rsa.sign(cipherText,localKeyPairRSA.getKeyPair().getPrivate()));

	}

	@Override
	public Pack register(Pack pack) {
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
		Object[] objects = rsa.decrypt(pack.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
		Pack packUsers;
		Authentication authentication = (Authentication) objects[0];
		if(authenticationController.register(authentication)){

			Boolean result = userController.createUser((User) objects[1]);
			byte[] cipherObject =  rsa.encrypt(result ,rsa.decodeBase64PublicKeyToPublicKey(authentication.getPublicKeyBase64()));
			byte[] signature = rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate());
			packUsers = new Pack(cipherObject,signature);

			return packUsers;

		}
		return null;
	}

	@Override
	public Pack sendMessage(Pack pack) throws RemoteException {
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
		Pack packMessage;
		byte[] cipherObject;

		Object[] objects = rsa.decrypt(pack.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
		UUID uuid = (UUID) objects[0];
		Session session = this.sessionController.getSession(uuid);
		Message message;
		System.out.println("hello from mainContr send 1");
		if(session !=null && rsa.checkSignature(pack.getSignature(), pack.getCipherObject(), session.getPublicKey())){
			System.out.println("hello from mainContr send 2");

			message = (Message) objects[1];
			System.out.println("size : " + ObjectConverter.objectToByteArray(message).length);

			System.out.println("hello from mainContr send 3");
			Message newMessage = this.messageController.sendMessage(message);
			System.out.println("hello from mainContr send 43");
			System.out.println("hello from mainContr send 43");
			System.out.println("hello from mainContr send 43");
			System.out.println("hello from mainContr send 43");

			cipherObject = rsa.encrypt(newMessage,session.getPublicKey());
			System.out.println("hello from mainContr send 3");
			System.out.println("hello from mainContr send 4");
			System.out.println("hello from mainContr send 4");

			packMessage = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
			System.out.println("hello from mainContr send 4");

			return packMessage;
		}
		System.out.println("hello from mainContr send 5");
		return null;
	}

	@Override
	public Pack updateMessage(Pack pack) throws RemoteException {
		RSA rsa = new RSA();
		LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
		Pack packResultUpdateMessage;
		byte[] cipherObject;
		Object[] objects = rsa.decrypt(pack.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
		UUID uuid = (UUID) objects[0];
		Session session = this.sessionController.getSession(uuid);
		Message message;

		if(session !=null && rsa.checkSignature(pack.getSignature(), pack.getCipherObject(), session.getPublicKey())){
			message = (Message) objects[1];
			Boolean results = messageController.updateMessage(message);
			cipherObject = rsa.encrypt(results,session.getPublicKey());
			packResultUpdateMessage = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
			return  packResultUpdateMessage;
		}
		return null;
	}

	@Override
	public String getPublicKey() {
		return authenticationController.getPublicKey();
	}
}
