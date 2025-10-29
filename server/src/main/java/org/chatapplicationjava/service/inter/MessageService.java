package org.chatapplicationjava.service.inter;

import org.chatapplicationjava.model.Message;

public interface MessageService {
    public Message sendMessage(Message message);
    public boolean updateMessage(Message message);
}
