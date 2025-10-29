package org.chatapplicationjava.main;


import org.chatapplicationjava.api.MainController;
import org.chatapplicationjava.api.MainControllerImpl;
import org.chatapplicationjava.concurrency.ListnerSocketClient;
import org.chatapplicationjava.model.Account;
import org.chatapplicationjava.model.Session;
import org.chatapplicationjava.model.User;
import org.chatapplicationjava.security.LocalKeyPairRSA;
import org.chatapplicationjava.service.impl.UserServiceImpl;
import org.chatapplicationjava.util.ServerContext;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.UUID;

public class Main {

   /* static {
        ConnectionDB.getConnection();

    }*/
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        LocalKeyPairRSA localKeyPairRSA = new LocalKeyPairRSA();
        localKeyPairRSA.generatePairKeys();
        ServerContext.bind("localKeyPairRSA",localKeyPairRSA);
        HashMap<UUID,Session> sessions = new HashMap<UUID,Session>();
        ServerContext.bind("sessions",sessions);

        // lance le listner de socket du server
        new Thread(new ListnerSocketClient()).start();


        //ServerChatApplication.main(args);

        //AccountDao compteDao = new AccountDao();
            //LocateRegistry.createRegistry(1090);
            //Naming.rebind("rmi://localhost:1090/c", compteDao);

       // UserServiceImpl utilisateurService = new UserServiceImpl();
       // boolean x = utilisateurService.createUser(new User("Khalil","Yassine", new Account("Khalil.chalati.07@gmail.com")));


        MainController mainController = new MainControllerImpl();
        LocateRegistry.createRegistry(2017);
        Naming.rebind("rmi://192.168.1.103:2017/api", mainController);
        System.out.println("demare");
    }
}
