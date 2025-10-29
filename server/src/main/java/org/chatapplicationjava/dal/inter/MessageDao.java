package org.chatapplicationjava.dal.inter;

import org.chatapplicationjava.model.Message;

public interface MessageDao {
    public Message saveMessage(Message message);
    public boolean updateMessage(Message message);
}
