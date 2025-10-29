package org.chatapplicationjava.model;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {

	private String email;
	private Date creationDate;

	public Account(String email, Date creationDate) {
		this.email = email;
		this.creationDate = creationDate;
	}

	public Account(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
