package org.chatapplicationjava.dal.impl;

import org.chatapplicationjava.dal.DataBase;
import org.chatapplicationjava.dal.inter.AccountDao;
import org.chatapplicationjava.model.Account;

import java.text.SimpleDateFormat;

public class AccountDaoImpl implements AccountDao {

	@Override
	public boolean saveCompte(Account account) {
		return DataBase.executeActionSQL("insert into Account(email_id, creation_date) values('" + account.getEmail() + "', '" + new SimpleDateFormat("yyyy-MM-dd").format(account.getCreationDate()) + "')");

	}

}
