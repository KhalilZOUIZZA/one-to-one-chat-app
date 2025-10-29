package org.chatapplicationjava.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Discussion implements Serializable {
    private User firstUser;
    private User secondUser;
    private ArrayList<Message> messages;

    public Discussion(User firstUser, User secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.messages = new ArrayList<Message>();
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
