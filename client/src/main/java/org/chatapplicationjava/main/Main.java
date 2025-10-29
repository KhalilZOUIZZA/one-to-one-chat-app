package org.chatapplicationjava.main;


import org.chatapplicationjava.model.Account;
import org.chatapplicationjava.model.User;
import org.chatapplicationjava.security.LocalKeyPairRSA;
import org.chatapplicationjava.security.RSA;
import org.chatapplicationjava.service.impl.AuthenticationServiceImpl;
import org.chatapplicationjava.service.impl.UserServiceImpl;
import org.chatapplicationjava.service.inter.AuthenticationService;
import org.chatapplicationjava.service.inter.UserService;
import org.chatapplicationjava.util.ClientContext;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class Main {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {

        // generer les paires cles
        LocalKeyPairRSA localKeyPairRSA = new LocalKeyPairRSA();
        localKeyPairRSA.generatePairKeys();
        ClientContext.bind("localKeyPairRSA",localKeyPairRSA);

        // get public key
        AuthenticationService authenticationService = new AuthenticationServiceImpl();
        ClientContext.bind("publicKeyServer",new RSA().decodeBase64PublicKeyToPublicKey(authenticationService.getPublicKey()));

        //

        // Define a custom permission representing AllPermission


        // Add the AllPermission to all codebases

        // Grant AllPermission to all codebases
        //System.out.println(x);
        //UtilisateurController utilisateurController = Communication.getStub("rmi://localhost:2019/uc");
       // System.out.println(utilisateurController);
        //utilisateurController.CreerUtilisateu(new Utilisateur("Chalati","Yassine",new Compte("Yassine.Chalati.07@gmail.com")));
        ClientChatApplication.main(args);
        //MainController x = (MainController) Communication.<Main>getStub("rmi://localhost:2017/api");
        //System.out.println(x);
        //UserService utilisateurService = new UserServiceImpl();
        //boolean resultat = utilisateurService.createUser(new User("Chalati","Yassine",new Account("Yassine.Chalati.07@gmail.com")));
        //System.out.println(resultat);

    }
}
