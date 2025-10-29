package org.chatapplicationjava.api;

import org.chatapplicationjava.network.Pack;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MainController extends Remote {

	//public boolean register(Pack pack);
	public Pack register(Pack pack) throws RemoteException;

	public Pack authenticate(Pack pack) throws RemoteException;

	//public ArrayList<User> searchContacts(String search, User user) throws RemoteException;
	public Pack searchContacts(Pack pack) throws RemoteException;

	//public boolean invitUser(User invite, User invitant) throws RemoteException;
	public Pack invitUser(Pack pack) throws RemoteException;

	//public boolean acceptUser(User invite, User invitant) throws RemoteException;
	public Pack acceptUser(Pack pack) throws RemoteException;

	//public boolean refuseUser(User invite, User invitant) throws RemoteException;
	public Pack refuseUser(Pack pack) throws RemoteException;

	public Pack sendMessage(Pack pack) throws RemoteException;
	public Pack updateMessage(Pack pack) throws RemoteException;

	public String getPublicKey() throws RemoteException;
}
