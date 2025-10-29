package org.chatapplicationjava.concurrency;

import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.model.Session;
import org.chatapplicationjava.network.Pack;
import org.chatapplicationjava.security.LocalKeyPairRSA;
import org.chatapplicationjava.security.RSA;
import org.chatapplicationjava.service.impl.MessageServiceImpl;
import org.chatapplicationjava.service.impl.SessionServiceImpl;
import org.chatapplicationjava.service.inter.MessageService;
import org.chatapplicationjava.service.inter.SessionService;
import org.chatapplicationjava.util.ServerContext;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.UUID;

public class SocketHandler implements Runnable{
    private final Socket socket;
    private UUID uuid;
    private SessionService sessionService;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    // il y a un probleme quand on a crypte les pendingMessage et les stocket crypte dans la file ->> le probleme c'est dans l'information du user que le message et envoye au client où il faut stocker les message sont les crypter ensuite l'evoye au client et informe le sender que le message est envoye au clien
    @Override
    public void run() {
        try {
            socket.setSoTimeout(20000);
            Pack pack = (Pack) (new ObjectInputStream(socket.getInputStream())).readObject();
            socket.setSoTimeout(0);
            RSA rsa = new RSA();
            LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ServerContext.lookup("localKeyPairRSA"));
            uuid = rsa.decrypt(pack.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
            System.out.println("from thread yesss 1");
            Pack packPendingMessages;
            byte[] cipherObject;

            sessionService = new SessionServiceImpl();
            Session session = sessionService.getSessions().get(uuid);
            System.out.println("from thread yesss 2");
            MessageService messageService = new MessageServiceImpl();
            System.out.println("from thread yesss 3");
            if (rsa.checkSignature(pack.getSignature(),pack.getCipherObject(),session.getPublicKey())){
                System.out.println("from thread yesss 4");
                session.setClientSocket(this.socket);
                System.out.println("from thread yesss 5");
                //ObjectInputStream objectInputStream = new ObjectInputStream();
                System.out.println("from thread yesss 5.5");
                //session.setObjectInputStream();
                session.setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
                for (Message message : session.getPendingMessages()){
                    // on crypte les messges et les envoyent au user
                    // ajouter un string pour desingner c'est un new message
                    cipherObject = rsa.encrypt(new Object[]{"NewMessge",message},session.getPublicKey());
                    packPendingMessages = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
                    //new ObjectOutputStream(this.socket.getOutputStream()).writeObject(packPendingMessages);
                    session.getObjectOutputStream().writeObject(packPendingMessages);
                    message.setSentToUser(true);
                    // inserrer les modificaiton de message en base des donnes
                    if(messageService.updateMessage(message)){
                        // informer le sender
                        for(Session sessionSender:sessionService.searchSession(message.getSenderUser().getAccount().getEmail())){
                            cipherObject = rsa.encrypt(new Object[]{"UpdateMessge",message},sessionSender.getPublicKey());
                            packPendingMessages = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));
                            new ObjectOutputStream(sessionSender.getClientSocket().getOutputStream()).writeObject(packPendingMessages);
                        }
                    }

                }
                session.setReadyForCommunicate(true);
                System.out.println("from thread yesss 6");
            } else {
                this.socket.close();
            }
        } catch (SocketTimeoutException e) {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        } catch (IOException | ClassNotFoundException e) {
            sessionService.getSessions().remove(uuid);
            throw new RuntimeException(e);
        }
    }
}
