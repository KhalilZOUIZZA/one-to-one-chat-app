package org.chatapplicationjava.dal.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.chatapplicationjava.dal.DataBase;
import org.chatapplicationjava.dal.inter.UserDao;
import org.chatapplicationjava.model.Account;
import org.chatapplicationjava.model.Discussion;
import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.model.User;

public class UserDaoImpl implements UserDao {

	@Override
	public boolean saveUser(User user) {
		return DataBase.executeActionSQL(
				"insert into User(EMAIL_ID,FIRST_NAME,LAST_NAME) values('" + user.getAccount().getEmail() + "', '"
						+ user.getLastName() + "', '" + user.getFirstName() + "')");
	}

	@Override
	public boolean insertInvitation(User guest, User inviter) {
		String[] querys;
		String queryGesut = "INSERT INTO Guest(inviter_email_id, Guest_email_id) VALUES ('"
				+ inviter.getAccount().getEmail() + "', '" + guest.getAccount().getEmail() + "'); ";

		String queryInviter = "INSERT INTO Inviter(Guest_email_id, inviter_email_id) VALUES ('"
				+ guest.getAccount().getEmail() + "', '" + inviter.getAccount().getEmail() + "');";
		querys = new String[]{queryGesut, queryInviter};

		return DataBase.executeActionSQL(querys);
	}

	@Override
	public boolean acceptInvitaion(User guest, User inviter) {

		String queryGuest = "DELETE FROM Guest WHERE Guest_email_id = '" + guest.getAccount().getEmail()
				+ "' AND inviter_email_id = '" + inviter.getAccount().getEmail()  + "';";

		String queryInviter = "DELETE FROM Inviter WHERE inviter_email_id = '" + inviter.getAccount().getEmail()
				+ "' AND Guest_email_id = '" + guest.getAccount().getEmail()  + "';";

		String queryContact = "INSERT INTO Contact (interlocutor1_email_id, interlocutor2_email_id) VALUES (" +
				"LEAST('" + guest.getAccount().getEmail() + "', '" + inviter.getAccount().getEmail() + "'), " +
				"GREATEST('" + guest.getAccount().getEmail() + "', '" + inviter.getAccount().getEmail() + "'));";

		String[] querys = new String[]{queryGuest, queryInviter, queryContact};

		return DataBase.executeActionSQL(querys);
	}

	@Override
	public boolean refuseInvitaion(User guest, User inviter) {
		String queryGuest = "DELETE FROM Guest WHERE Guest_email_id = '" + guest.getAccount().getEmail()
				+ "' AND inviter_email_id = '" + inviter.getAccount().getEmail()  + "';";

		String queryInviter = "DELETE FROM Inviter WHERE inviter_email_id = '" + inviter.getAccount().getEmail()
				+ "' AND Guest_email_id = '" + guest.getAccount().getEmail()  + "';";

		String[] querys = new String[]{queryGuest, queryInviter};

		return DataBase.executeActionSQL(querys);
	}

	/*@Override
	public boolean blockContact(User blocker, User blocked) {
		return false;
	}*/

	@Override
	public ArrayList<User> findUserByNameOrLastName(String keySearch1, String keySearch2, String keySearch3, User user) {


		String query = "SELECT * FROM User " +
				"LEFT JOIN (" +
					"SELECT " +
						"CASE " +
							"WHEN interlocutor1_email_id = '" + user.getAccount().getEmail() + "' THEN interlocutor2_email_id " +
							"WHEN interlocutor2_email_id = '" + user.getAccount().getEmail() + "' THEN interlocutor1_email_id " +
						"END AS email_id " +
					"FROM Contact " +
					"WHERE interlocutor1_email_id = '" + user.getAccount().getEmail() + "' OR interlocutor2_email_id = '" + user.getAccount().getEmail() +"'" +
				") Contact " +
				"ON User.email_id = Contact.email_id "  +
				"WHERE ";

		if (!keySearch1.isEmpty()) {
			query += "CONCAT(User.last_name, ' ', User.first_name) LIKE '%" + keySearch1 + "%' ";
		}
		if (!keySearch2.isEmpty()) {
			query += "AND CONCAT(User.last_name, ' ', User.first_name) LIKE '%" + keySearch2 + "%' ";
		}
		if (!keySearch3.isEmpty()) {
			query += "AND CONCAT(User.last_name, ' ', User.first_name) LIKE '%" + keySearch3 + "%' ";
		}

		query += "AND Contact.email_id IS NULL;";

		System.out.println(query);
		ResultSet resultSet = DataBase.executeSelectSQL(query);
		return this.getArrayUtilisateurs(resultSet);

	}
	/*
	SELECT * FROM user WHERE
	CONCAT(last_name, ' ', first_name) LIKE '%keySearch1%'
	AND CONCAT(last_name, ' ', first_name) LIKE '%keySearch2%'
	AND CONCAT(last_name, ' ', first_name) LIKE '%keySearch3%';


	*/

	@Override
	public User findUserByEmail(String email) {
		String query = "SELECT * FROM User WHERE email_id = '"
				+ email
				+ "';";
		ResultSet resultSet = DataBase.executeSelectSQL(query);
		try {
			if(resultSet.next())
				return new User(resultSet.getString("first_name"), resultSet.getString("last_name"), new Account(resultSet.getString("email_id")));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		System.out.println("non User");
		return null;
	}

	@Override
	public ArrayList<User> findContactsUserByEmail(User user) {
		String query = "SELECT U.first_name, U.last_name, M.* FROM User U, ( " +
							"SELECT " +
								"CASE " +
									"WHEN participant1 = '" + user.getAccount().getEmail() + "' THEN participant2 " +
									"WHEN participant2 = '" + user.getAccount().getEmail() + "' THEN participant1 " +
								"END AS email_id, " +
								"message_id, " +
								"sender, " +
								"sending_date, " +
								"text, " +
								"seen, " +
								"sentToUser, " +
								"sentToServer " +
							"FROM Message " +
							"WHERE participant1 = '" + user.getAccount().getEmail() + "' OR participant2 = '" + user.getAccount().getEmail() + "'" +
							") M " +
						"WHERE U.email_id IN ( " +
							"SELECT " +
								"CASE " +
									"WHEN interlocutor1_email_id = '" + user.getAccount().getEmail() + "' THEN interlocutor2_email_id " +
									"WHEN interlocutor2_email_id = '" + user.getAccount().getEmail() + "' THEN interlocutor1_email_id " +
								"END AS email_id " +
							"FROM Contact " +
							"WHERE interlocutor1_email_id = '" + user.getAccount().getEmail() + "' OR interlocutor2_email_id = '" + user.getAccount().getEmail() +"'" +
						") " +
						"AND U.email_id = M.email_id ORDER BY U.first_name, U.last_name, M.email_id, M.message_id;";
		ResultSet resultSet = DataBase.executeSelectSQL(query);

		return this.getArrayContacAndDiscussion(resultSet, user);
	}

	@Override
	public ArrayList<User> findInviterUserByEmail(User user) {
		String query = "SELECT U.first_name, U.last_name, U.email_id FROM User U, (" +
					"SELECT " +
					"inviter_email_id " +

					"FROM Inviter " +
					"WHERE Guest_email_id = '" + user.getAccount().getEmail() + "'" +
				") I " +
				"WHERE U.email_id = I.inviter_email_id;" ;
		ResultSet resultSet = DataBase.executeSelectSQL(query);

		return this.getArrayUtilisateurs(resultSet);
	}

	/*
	* select u.first_name, u.last_name, u.email_id from User u , (select inviter_email_id from Inviter where Guest_email_id = 'user3@email.com') i where u.email_id = i.inviter_email_id; */


	@Override
	public ArrayList<User> findGuestUserByEmail(User user) {
		String query = "SELECT U.first_name, U.last_name, U.email_id FROM User U, (" +
				"SELECT " +
				"Guest_email_id " +

				"FROM Guest " +
				"WHERE Inviter_email_id = '" + user.getAccount().getEmail() + "'" +
				") I " +
				"WHERE U.email_id = I.Guest_email_id;" ;
		ResultSet resultSet = DataBase.executeSelectSQL(query);
		return this.getArrayUtilisateurs(resultSet);
	}
	/*
	* SELECT U.first_name, U.last_name, M.*	FROM User U, (
		SELECT
			CASE
				WHEN participant1 = 'user3@email.com' THEN participant2
				WHEN participant2 = 'user3@email.com' THEN participant1
			END AS email_id,
			sender,
			sending_date,
			text,
			seen,
			sentToUser,
			sentToServer
		FROM Message
		WHERE participant1 = 'user3@email.com' OR participant2 = 'user3@email.com'
	) M
	WHERE U.email_id IN (
		SELECT
			CASE
				WHEN interlocutor1_email_id = 'user3@email.com' THEN interlocutor2_email_id
				WHEN interlocutor2_email_id = 'user3@email.com' THEN interlocutor1_email_id
			END AS email_id
		FROM Contact
		WHERE interlocutor1_email_id = 'user3@email.com' OR interlocutor2_email_id = 'user3@email.com'
	) and U.email_id = M.email_id ORDER BY U.first_name, U.last_name, M.email_id, M.sending_date;
	* */

	private ArrayList<User> getArrayUtilisateurs(ResultSet resultSet){
		ArrayList<User> users = new ArrayList<>();
		try {
			while (resultSet.next()) {
				String lastName = resultSet.getString("LAST_NAME");
				String firstName = resultSet.getString("FIRST_NAME");
				String email = resultSet.getString("EMAIL_ID");

				User user = new User(firstName, lastName, new Account(email));
				System.out.println(user.getAccount().getEmail());
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
		}
		return null;
	}

	private ArrayList<User> getArrayContacAndDiscussion(ResultSet resultSet, User user){
		ArrayList<User> users = new ArrayList<>();
		User contact;

		try {
			while (resultSet.next()) {

				String lastName = resultSet.getString("LAST_NAME");
				String firstName = resultSet.getString("FIRST_NAME");
				String email = resultSet.getString("EMAIL_ID");
				contact = new User(firstName, lastName, new Account(email));
				contact.getDiscussions().add(new Discussion(user,contact));
				contact.getDiscussions().get(0).getMessages().add(new Message(resultSet.getInt("message_id"), new User(new Account(resultSet.getString("sender"))), resultSet.getString("text"), resultSet.getDate("sending_date"), resultSet.getBoolean("sentToServer"), resultSet.getBoolean("sentToUser"), resultSet.getBoolean("seen"), contact.getDiscussions().get(0)));
				while (resultSet.next()){
					if(contact.getAccount().getEmail().equals(resultSet.getString("EMAIL_ID")))
						contact.getDiscussions().get(0).getMessages().add(new Message(resultSet.getInt("message_id"), new User(new Account(resultSet.getString("sender"))), resultSet.getString("text"), resultSet.getDate("sending_date"), resultSet.getBoolean("sentToServer"), resultSet.getBoolean("sentToUser"), resultSet.getBoolean("seen"), contact.getDiscussions().get(0)));
					else {
						resultSet.previous();
						break;
					}
				}
				users.add(contact);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
		}
		return null;
	}

}