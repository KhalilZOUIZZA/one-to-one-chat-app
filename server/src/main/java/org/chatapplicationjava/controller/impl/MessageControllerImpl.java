package org.chatapplicationjava.controller.impl;

import org.chatapplicationjava.controller.inter.MessageController;
import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.model.Session;
import org.chatapplicationjava.network.Pack;
import org.chatapplicationjava.security.LocalKeyPairRSA;
import org.chatapplicationjava.security.RSA;
import org.chatapplicationjava.service.impl.MessageServiceImpl;
import org.chatapplicationjava.service.impl.SessionServiceImpl;
import org.chatapplicationjava.service.inter.MessageService;
import org.chatapplicationjava.service.inter.SessionService;
import org.chatapplicationjava.util.ObjectConverter;
import org.chatapplicationjava.util.ServerContext;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MessageControllerImpl implements MessageController {

    private MessageService messageService;
    private SessionService sessionService;

    public MessageControllerImpl() {
        this.messageService = new MessageServiceImpl();
        this.sessionService = new SessionServiceImpl();
    }

    @Override
    public Message sendMessage(Message message) {
        Message newMessageVersion = messageService.sendMessage(message);

        ArrayList<Session> sessionsUser = sessionService.searchSession(message.getDiscussion().getFirstUser().getAccount().getEmail().equals(message.getSenderUser().getAccount().getEmail()) ? message.getDiscussion().getSecondUser().getAccount().getEmail() : message.getDiscussion().getFirstUser().getAccount().getEmail());

        RSA rsa;
        LocalKeyPairRSA localKeyPairRSA;
        Pack pack;
        byte[] cipherObject;

        if(sessionsUser.isEmpty())
            return newMessageVersion;

        rsa = new RSA();
        localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
        //pack = new Pack(rsa.encrypt(message,), );


        for (Session session : sessionsUser){
            if(!session.isReadyForCommunicate())
                session.getPendingMessages().add(message);
            else {
                cipherObject = rsa.encrypt(new Object[]{"NewMessge",message},session.getPublicKey());
                pack = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
                try {
                    session.getObjectOutputStream().writeObject(pack);
                    message.setSentToUser(true);
                } catch (IOException e) {
                    sessionsUser.remove(session);

                    return this.sendMessage(message);
                    //throw new RuntimeException(e);
                }
            }
        }
        System.out.println("hello from messCont send 4");

        return message;
    }

    @Override
    public boolean updateMessage(Message message) {

        byte[] cipherObject;
        Pack updateMessage;
        RSA rsa = new RSA();
        LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));

        if(messageService.updateMessage(message)){
            // informer le sender
            for(Session sessionSender:sessionService.searchSession(message.getSenderUser().getAccount().getEmail())){
                if(sessionSender.isReadyForCommunicate()) {
                    cipherObject = rsa.encrypt(new Object[]{"UpdateMessge", message}, sessionSender.getPublicKey());
                    updateMessage = new Pack(cipherObject, rsa.sign(cipherObject, localKeyPairRSA.getKeyPair().getPrivate()));
                    try {
                        new ObjectOutputStream(sessionSender.getClientSocket().getOutputStream()).writeObject(updateMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
