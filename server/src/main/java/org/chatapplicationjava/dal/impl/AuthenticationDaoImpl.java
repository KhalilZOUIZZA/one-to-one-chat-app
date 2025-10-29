package org.chatapplicationjava.dal.impl;

import org.chatapplicationjava.dal.DataBase;
import org.chatapplicationjava.dal.inter.AuthenticationDao;
import org.chatapplicationjava.model.Authentication;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationDaoImpl implements AuthenticationDao {

	@Override
	public Authentication findByPassword(Authentication authentication) {

		String query = "SELECT email_id  FROM Authentication WHERE email_id = '"
				+ authentication.getEmail()  + "' and password = '"
				+ authentication.getPassword() + "';";
		ResultSet resultSet = DataBase.executeSelectSQL(query);
		try {
			if(resultSet.next())
				return new Authentication(resultSet.getString("email_id"),authentication.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		System.out.println("non authentification");
		return null;
	}

	@Override
	public boolean saveAuthentication(Authentication authentication) {
		String query = "INSERT INTO Authentication(email_id, password) VALUES ('"
				+ authentication.getEmail()
				+ "', '"
				+ authentication.getPassword()
				+ "');";
		return DataBase.executeActionSQL(query);
	}

}
