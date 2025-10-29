package org.chatapplicationjava.controller.inter;

import org.chatapplicationjava.model.Message;

public interface MessageController {
    public Message sendMessage(Message message);
    public boolean updateMessage(Message message);
}
