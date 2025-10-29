package org.chatapplicationjava.dal.inter;

import org.chatapplicationjava.model.Authentication;

import java.io.Serializable;

public interface AuthenticationDao {

	public Authentication findByPassword(Authentication authentication);
	public boolean saveAuthentication(Authentication authentication);
}
