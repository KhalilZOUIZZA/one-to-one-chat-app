package org.chatapplicationjava.model;

import org.chatapplicationjava.network.Pack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

public class Session {
    private UUID idSession;
    private User user;
    private PublicKey publicKey;
    private Socket clientSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean readyForCommunicate = false;
    //private boolean inLoadData = false;
    private Queue<Message> pendingMessages;

    //Constructor


    public Session(UUID idSession) {
        this.idSession = idSession;
        this.pendingMessages = new PriorityQueue<>();
    }

    public Session(UUID idSession, User user, PublicKey publicKey) {
        this.idSession = idSession;
        this.user = user;
        this.publicKey = publicKey;
        this.pendingMessages = new PriorityQueue<>();
        //this.inLoadData = inLoadData;
    }


    // geter setter


    public UUID getIdSession() {
        return idSession;
    }

    public void setIdSession(UUID idSession) {
        this.idSession = idSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public boolean isReadyForCommunicate() {
        return readyForCommunicate;
    }

    public void setReadyForCommunicate(boolean readyForCommunicate) {
        this.readyForCommunicate = readyForCommunicate;
    }

    /*public boolean isInLoadData() {
        return inLoadData;
    }

    public void setInLoadData(boolean inLoadData) {
        this.inLoadData = inLoadData;
    }*/

    public Queue<Message> getPendingMessages() {
        return pendingMessages;
    }

    public void setPendingMessages(Queue<Message> pendingMessages) {
        this.pendingMessages = pendingMessages;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }
}
