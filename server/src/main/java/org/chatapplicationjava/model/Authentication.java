package org.chatapplicationjava.model;

import java.io.Serializable;

public class Authentication implements Serializable {

	private String email;
	private String password;
	private String publicKeyBase64;

	public Authentication(String email, String password, String publicKeyBase64) {
		this.email = email;
		this.password = password;
		this.publicKeyBase64 = publicKeyBase64;
	}
	public Authentication(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPublicKeyBase64() {
		return publicKeyBase64;
	}

	public void setPublicKeyBase64(String publicKeyBase64) {
		this.publicKeyBase64 = publicKeyBase64;
	}
}
