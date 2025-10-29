package org.chatapplicationjava.dal.impl;

import org.chatapplicationjava.dal.DataBase;
import org.chatapplicationjava.dal.inter.MessageDao;
import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.util.ObjectConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class MessageDaoImpl implements MessageDao {
    @Override
    public Message saveMessage(Message message) {

        String query= "INSERT INTO Message(participant1, participant2, sender, sending_date, text, seen, sentToUser, sentToServer) VALUES(" +
                "LEAST('" + message.getDiscussion().getFirstUser().getAccount().getEmail() + "', '" + message.getDiscussion().getSecondUser().getAccount().getEmail() + "'), " +
                "GREATEST('" + message.getDiscussion().getFirstUser().getAccount().getEmail() + "', '" + message.getDiscussion().getSecondUser().getAccount().getEmail() + "'), '" +
                //message.getDiscussion().getFirstUser().getAccount().getEmail() + "', '" +
                //message.getDiscussion().getSecondUser().getAccount().getEmail() + "', '" +
                message.getSenderUser().getAccount().getEmail() + "', '" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message.getSendingDate()) + "', '" +
                message.getText() + "', " +
                (message.isSeen()?"1":"0") + ", " +
                (message.isSentToUser()?"1":"0") + ", " +
                (message.isSentToServer()?"1":"0") + ");";
        System.out.println(query);
        try {
            ResultSet resultSet = DataBase.executeActionSQLAndReturnGeneratedKey(query);
            System.out.println(resultSet);
            if( resultSet != null) {
                resultSet.next();
                System.out.println("size : " + ObjectConverter.objectToByteArray(message).length);
                message.setIdMessage(resultSet.getInt(1));
                //System.out.println(message.getIdMessage());
                System.out.println("size : " + ObjectConverter.objectToByteArray(message).length);
                return message;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateMessage(Message message) {
        String query = "UPDATE Message SET sentToUser = " +
                (message.isSentToUser()? 1 : 0) + ", " +
                "seen = " +
                (message.isSeen()? 1 : 0) +
                " WHERE message_id = " +
                message.getIdMessage() + ";";

        return DataBase.executeActionSQL(query);
    }
}
