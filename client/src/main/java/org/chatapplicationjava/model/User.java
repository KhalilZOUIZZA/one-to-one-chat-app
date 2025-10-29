package org.chatapplicationjava.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class User implements Serializable {

	private String firstName;
	private String lastName;
	private Account account;
	private ArrayList<User> Guests;
	private ArrayList<User> Inviters;
	private ArrayList<User> contacts;
	private ArrayList<Discussion> discussions;



	public User(String firstName, String lastName, Account account) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.account = account;
		this.Guests = new ArrayList<User>();
		this.Inviters = new ArrayList<User>();
		this.contacts = new ArrayList<User>();
		this.discussions = new ArrayList<Discussion>();
	}

	public User(Account account) {
		this.account = account;
		this.Guests = new ArrayList<User>();
		this.Inviters = new ArrayList<User>();
		this.contacts = new ArrayList<User>();
		this.discussions = new ArrayList<Discussion>();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ArrayList<User> getGuests() {
		return Guests;
	}

	public void setGuests(ArrayList<User> guests) {
		Guests = guests;
	}

	public ArrayList<User> getInviters() {
		return Inviters;
	}

	public void setInviters(ArrayList<User> inviters) {
		Inviters = inviters;
	}

	public ArrayList<User> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<User> contacts) {
		this.contacts = contacts;
	}

	public ArrayList<Discussion> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(ArrayList<Discussion> discussions) {
		this.discussions = discussions;
	}
}
