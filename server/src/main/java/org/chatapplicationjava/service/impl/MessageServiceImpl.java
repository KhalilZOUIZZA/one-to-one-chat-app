package org.chatapplicationjava.service.impl;

import org.chatapplicationjava.dal.impl.MessageDaoImpl;
import org.chatapplicationjava.dal.inter.MessageDao;
import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.service.inter.MessageService;

import java.util.Date;

public class MessageServiceImpl implements MessageService {
    @Override
    public Message sendMessage(Message message) {

        MessageDao messageDao = new MessageDaoImpl();

        message.setSentToServer(true);
        message.setSendingDate(new Date());

        message = messageDao.saveMessage(message);
        return message;
    }

    @Override
    public boolean updateMessage(Message message) {
        MessageDao messageDao = new MessageDaoImpl();
        return messageDao.updateMessage(message);
    }
}
