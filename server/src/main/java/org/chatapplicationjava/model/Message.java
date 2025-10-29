package org.chatapplicationjava.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private int idMessage;
    private User senderUser;
    private String text;
    private Date sendingDate;
    private boolean sentToServer;
    private boolean sentToUser;
    private boolean seen;
    private Discussion discussion;

    public Message(int idMessage ,User senderUser, String text, Date sendingDate, boolean sentToServer, boolean sentToUser, boolean seen, Discussion discussion) {
        this.idMessage = idMessage;
        this.senderUser = senderUser;
        this.text = text;
        this.sendingDate = sendingDate;
        this.sentToServer = sentToServer;
        this.sentToUser = sentToUser;
        this.seen = seen;
        this.discussion = discussion;
    }

    public Message(User senderUser, Date sendingDate, Discussion discussion) {
        this.senderUser = senderUser;
        this.text = "";
        this.sendingDate = sendingDate;
        this.sentToServer = true;
        this.sentToUser = true;
        this.seen = true;
        this.discussion = discussion;
    }

    public Message(User senderUser, String text, Discussion discussion) {
        this.senderUser = senderUser;
        this.text = text;
        this.discussion = discussion;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }

    public boolean isSentToServer() {
        return sentToServer;
    }

    public void setSentToServer(boolean sentToServer) {
        this.sentToServer = sentToServer;
    }

    public boolean isSentToUser() {
        return sentToUser;
    }

    public void setSentToUser(boolean sentToUser) {
        this.sentToUser = sentToUser;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }
}
