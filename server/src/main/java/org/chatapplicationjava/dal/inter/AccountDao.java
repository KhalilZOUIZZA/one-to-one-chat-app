package org.chatapplicationjava.dal.inter;

import org.chatapplicationjava.model.Account;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AccountDao {

	public boolean saveCompte(Account account);
}
