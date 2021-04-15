package com.DyniaServer.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DyniaServer {
    private int serverPort=12129;
    public void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is open");
            List.loadResults();
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }
        }
        catch (IOException e){
            System.out.println("Can't start the server");
            System.err.println(e);
        }
    }

}
