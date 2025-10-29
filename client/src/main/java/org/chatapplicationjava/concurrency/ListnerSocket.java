package org.chatapplicationjava.concurrency;

import org.chatapplicationjava.api.MainController;
import org.chatapplicationjava.communication.Communication;
import org.chatapplicationjava.controller.HomePageController;
import org.chatapplicationjava.model.Discussion;
import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.model.User;
import org.chatapplicationjava.network.Pack;
import org.chatapplicationjava.security.LocalKeyPairRSA;
import org.chatapplicationjava.security.RSA;
import org.chatapplicationjava.util.ClientContext;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.UUID;

public class ListnerSocket implements Runnable{

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream ;

    @Override
    public void run() {
        try {
            Socket socket = new Socket("192.168.1.103",2019);
            //BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            RSA rsa = new RSA();
            Pack pack ;
            User user;
            user = ((User) ClientContext.lookup("user"));
            Message message;
            Pack packResult ;
            boolean resultBoolean1;
            boolean resultBoolean2;
            Object[] objects;
            LocalKeyPairRSA localKeyPairRSA = ((LocalKeyPairRSA) ClientContext.lookup("localKeyPairRSA"));
            UUID uuid = (UUID) ClientContext.lookup("uuid");
            PublicKey publicKeyServer = (PublicKey) ClientContext.lookup("publicKeyServer");

            byte[] cipherObject = rsa.encrypt(uuid,publicKeyServer);

            pack = new Pack(cipherObject,rsa.sign(cipherObject,localKeyPairRSA.getKeyPair().getPrivate()));

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(pack);

            objectInputStream = new ObjectInputStream(socket.getInputStream());

            //= new ObjectInputStream(socket.getInputStream());
            while (true){
                packResult = (Pack) objectInputStream.readObject();
               if(rsa.checkSignature(packResult.getSignature(),packResult.getCipherObject(),publicKeyServer)) {
                   objects = rsa.decrypt(packResult.getCipherObject(), localKeyPairRSA.getKeyPair().getPrivate());
                   switch ((String) objects[0]){
                       case "NewMessge" :{
                           message = (Message) objects[1];
                           for (User contact: user.getContacts()){
                               resultBoolean1 = (contact.getDiscussions().get(0).getFirstUser().getAccount().getEmail().equals(message.getDiscussion().getFirstUser().getAccount().getEmail()) && contact.getDiscussions().get(0).getSecondUser().getAccount().getEmail().equals(message.getDiscussion().getSecondUser().getAccount().getEmail()));
                               resultBoolean2 = (contact.getDiscussions().get(0).getFirstUser().getAccount().getEmail().equals(message.getDiscussion().getSecondUser().getAccount().getEmail()) && contact.getDiscussions().get(0).getSecondUser().getAccount().getEmail().equals(message.getDiscussion().getFirstUser().getAccount().getEmail()));

                               if( resultBoolean1 || resultBoolean2){
                                   contact.getDiscussions().get(0).getMessages().add(message);
                                   ((HomePageController)ClientContext.lookup("homePageController")).refreshUser(contact);
                                   break;
                              }
                           }
                       }
                   }
                   
                   //System.out.println(reader.readLine());
               }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
