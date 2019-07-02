package network.server;

import View.CommandHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    private static final int DEFAULT_PORT=8000;
    private static ServerSocket server;
    private static CommandHandler commandHandler;

    public static void main(String[] args) throws IOException {
        server=new ServerSocket(DEFAULT_PORT);
        while(true){
            Socket client=server.accept();
            new Thread(() -> communicate(client)).start();
        }
    }

    private static void communicate(Socket client) {

    }
}
