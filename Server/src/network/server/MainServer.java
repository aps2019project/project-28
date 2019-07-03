package network.server;

import View.MessageHandler;
import network.Message;
import network.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    private static final int DEFAULT_PORT=8000;
    private static ServerSocket server;
    private static MessageHandler messageHandler=new MessageHandler();

    public static void main(String[] args) throws IOException {
        server=new ServerSocket(DEFAULT_PORT);
        while(true){
            System.err.println("waiting for connection");
            Socket socket=server.accept();
            Client client=new Client(socket);
            System.err.println("client found");
            new Thread(() -> communicate(client)).start();
        }
    }

    private static void communicate(Client client){
        while(true){
                Message message= client.read();
                System.err.println("message read");
                System.out.println("message.getText() = " + message.getText());
                handleMessage(message,client);
            }
    }

    private static void handleMessage(Message message, Client client) {
        Message respond=messageHandler.handleMessage(message);
        System.out.println("respont vaghT mikhay benevisish");
        System.out.println("respond = " + respond);
            client.write(respond);

    }
}
