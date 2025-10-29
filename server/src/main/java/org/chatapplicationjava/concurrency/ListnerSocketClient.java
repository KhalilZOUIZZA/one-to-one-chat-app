package org.chatapplicationjava.concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListnerSocketClient implements Runnable{
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(2019);
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(new SocketHandler(socket)).start();
                System.out.println("listner haha");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
