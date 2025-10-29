package org.chatapplicationjava.service.impl;

import org.chatapplicationjava.api.MainController;
import org.chatapplicationjava.communication.Communication;
import org.chatapplicationjava.model.Authentication;
import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.model.User;
import org.chatapplicationjava.network.Pack;
import org.chatapplicationjava.security.LocalKeyPairRSA;
import org.chatapplicationjava.security.RSA;
import org.chatapplicationjava.service.inter.UserService;
import org.chatapplicationjava.util.ClientContext;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public boolean register(Authentication authentication, User user) {
        try {
            RSA rsa = new RSA();
            LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ClientContext.lookup("localKeyPairRSA"));
            MainController mainController = Communication.getStub("rmi://192.168.1.103:2017/api");
            Object[] objects = {authentication, user};
            Pack pack ;
            authentication.setPublicKeyBase64(localKeyPairRSA.getPublicKeyBase64());
            PublicKey publicKeyServer = (PublicKey) ClientContext.lookup("publicKeyServer");
            byte[] cipherObject = rsa.encrypt(objects,publicKeyServer);

            pack = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
            Pack packResult = mainController.register(pack);
            Boolean resultDecrypted = false;
            if(rsa.checkSignature(packResult.getSignature(),packResult.getCipherObject(),publicKeyServer))
                resultDecrypted = rsa.decrypt(packResult.getCipherObject(),localKeyPairRSA.getKeyPair().getPrivate());

            return resultDecrypted;
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean authenticate(Authentication authentication) {
        try {
            RSA rsa = new RSA();
            Pack pack ;
            LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ClientContext.lookup("localKeyPairRSA"));
            MainController mainController = Communication.getStub("rmi://192.168.1.103:2017/api");
            Object[] objects;
            UUID uuid;
            User user;

            authentication.setPublicKeyBase64(localKeyPairRSA.getPublicKeyBase64());
            PublicKey publicKeyServer = (PublicKey) ClientContext.lookup("publicKeyServer");
            byte[] cipherObject = rsa.encrypt(authentication,publicKeyServer);

            pack = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
            Pack packResult = mainController.authenticate(pack);
            if(rsa.checkSignature(packResult.getSignature(),packResult.getCipherObject(),publicKeyServer)) {
                objects = rsa.decrypt(packResult.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
                uuid = (UUID) objects[0];
                user = (User) objects[1];
                ClientContext.bind("uuid",uuid);
                ClientContext.bind("user",user);
                return true;
            }
            return false;
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public ArrayList<User> searchContacts(String search) {
        // envoyer trois chose UUID USER STRING
        try {
            RSA rsa = new RSA();
            Pack pack ;
            LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ClientContext.lookup("localKeyPairRSA"));
            MainController mainController = Communication.getStub("rmi://192.168.1.103:2017/api");
            UUID uuid = (UUID) ClientContext.lookup("uuid");
            User user = (User) ClientContext.lookup("user");
            User userInformation  = new User(user.getFirstName(), user.getLastName(), user.getAccount());
            Object[] objects = {uuid,search,userInformation};
            PublicKey publicKeyServer = (PublicKey) ClientContext.lookup("publicKeyServer");

            byte[] cipherObject = rsa.encrypt(objects,publicKeyServer);

            pack = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
            Pack packResult = mainController.searchContacts(pack);
            if(rsa.checkSignature(packResult.getSignature(),packResult.getCipherObject(),publicKeyServer)) {
                ArrayList<User> users = rsa.decrypt(packResult.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
                return users;
            }
            return null;
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean invitUser(User guest) {
        try {
            RSA rsa = new RSA();
            Pack pack ;
            LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ClientContext.lookup("localKeyPairRSA"));
            MainController mainController = Communication.getStub("rmi://192.168.1.103:2017/api");
            UUID uuid = (UUID) ClientContext.lookup("uuid");
            User user = (User) ClientContext.lookup("user");
            User userInformation  = new User(user.getFirstName(), user.getLastName(), user.getAccount());
            Object[] objects = {uuid,guest,userInformation};
            System.out.println(guest.getFirstName() + guest.getLastName() + guest.getAccount().getEmail());
            PublicKey publicKeyServer = (PublicKey) ClientContext.lookup("publicKeyServer");

            byte[] cipherObject = rsa.encrypt(objects,publicKeyServer);

            pack = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
            Pack packResult = mainController.invitUser(pack);
            if(rsa.checkSignature(packResult.getSignature(),packResult.getCipherObject(),publicKeyServer)) {
                Boolean resultDecrypted = false;
                resultDecrypted    = rsa.decrypt(packResult.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
                return resultDecrypted;
            }
            return false;
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean acceptUser(User inviter) {
        try {
            RSA rsa = new RSA();
            Pack pack ;
            LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ClientContext.lookup("localKeyPairRSA"));
            MainController mainController = Communication.getStub("rmi://192.168.1.103:2017/api");
            UUID uuid = (UUID) ClientContext.lookup("uuid");
            User user = (User) ClientContext.lookup("user");
            User userInformation  = new User(user.getFirstName(), user.getLastName(), user.getAccount());
            Object[] objects = {uuid,userInformation,inviter};
            PublicKey publicKeyServer = (PublicKey) ClientContext.lookup("publicKeyServer");

            byte[] cipherObject = rsa.encrypt(objects,publicKeyServer);

            pack = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
            Pack packResult = mainController.acceptUser(pack);
            if(rsa.checkSignature(packResult.getSignature(),packResult.getCipherObject(),publicKeyServer)) {
                Boolean resultDecrypted = rsa.decrypt(packResult.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
                return resultDecrypted;
            }
            return false;
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean refuseUser(User inviter) {
        try {
            RSA rsa = new RSA();
            Pack pack ;
            LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ClientContext.lookup("localKeyPairRSA"));
            MainController mainController = Communication.getStub("rmi://192.168.1.103:2017/api");
            UUID uuid = (UUID) ClientContext.lookup("uuid");
            User user = (User) ClientContext.lookup("user");
            User userInformation  = new User(user.getFirstName(), user.getLastName(), user.getAccount());
            Object[] objects = {uuid,userInformation,inviter};
            PublicKey publicKeyServer = (PublicKey) ClientContext.lookup("publicKeyServer");

            byte[] cipherObject = rsa.encrypt(objects,publicKeyServer);

            pack = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
            Pack packResult = mainController.refuseUser(pack);
            if(rsa.checkSignature(packResult.getSignature(),packResult.getCipherObject(),publicKeyServer)) {
                Boolean resultDecrypted = rsa.decrypt(packResult.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
                return resultDecrypted;
            }
            return false;
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Message sendMessage(Message message) {
        try {
            RSA rsa = new RSA();
            Pack pack ;
            LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ClientContext.lookup("localKeyPairRSA"));
            MainController mainController = Communication.getStub("rmi://192.168.1.103:2017/api");
            UUID uuid = (UUID) ClientContext.lookup("uuid");
            Object[] objects = {uuid,message};
            PublicKey publicKeyServer = (PublicKey) ClientContext.lookup("publicKeyServer");

            byte[] cipherObject = rsa.encrypt(objects,publicKeyServer);

            pack = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
            Pack packResult = mainController.sendMessage(pack);
            if(rsa.checkSignature(packResult.getSignature(),packResult.getCipherObject(),publicKeyServer)) {
                return rsa.decrypt(packResult.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
            }
            return null;
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
        return null;
    }


    // private final String objectAdress = "rmi://localhost:2017/api";
/*
	@Override
	public boolean createUser(User user) {
		try {
			MainController mainController = Communication.<MainController>getStub("rmi://localhost:2017/api");
			return (mainController.createUser(user));
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.getStackTrace();
			// throw new RuntimeException(e);
		}
		return false;
	}

	@Override
	public ArrayList<User> searchContacts(String search) {
		try {
			MainController mainController = Communication.<MainController>getStub("rmi://localhost:2017/api");
			return (mainController.searchContacts(search));
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.getStackTrace();
		}
		return null;

	}

	@Override
	public boolean invitUser(User invite, User invitant) {
		try {
			MainController mainController = Communication.<MainController>getStub("rmi://localhost:2017/api");
			return (mainController.invitUser(invite, invitant));
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.getStackTrace();
		}
		return false;
	}

	@Override
	public boolean acceptUser(User invite, User invitant) {
		try {
			MainController mainController = Communication.<MainController>getStub("rmi://localhost:2017/api");
			return (mainController.acceptUser(invite, invitant));
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.getStackTrace();
		}
		return false;
	}

	@Override
	public boolean refuseUser(User invite, User invitant) {
		try {
			MainController mainController = Communication.<MainController>getStub("rmi://localhost:2017/api");
			return (mainController.refuseUser(invite, invitant));
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.getStackTrace();
		}
		return false;
	}
*/
}
