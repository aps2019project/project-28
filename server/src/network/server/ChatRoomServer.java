package network.server;

import network.ChatMSG;
import network.Message;
import network.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoomServer {
    private static ServerSocket server;
    private static ArrayList<Client>clients=new ArrayList<>();
    public static void main(String[] args) {
        try {
            server=new ServerSocket(8585);
            while (true){
                Socket socket=server.accept();
                Client client=new Client(socket);
                clients.add(client);
                new Thread(() -> communicate(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void communicate(Client client) {
        while (true){
            Message message=client.read();

            ChatMSG msg = (ChatMSG) message.getCarry().get(0);
            updateChatRoom(msg);
        }
    }

    private static void updateChatRoom(ChatMSG msg) {
        synchronized (clients){
            Message message=new Message("chat!");
            message.addCarry(msg);
            clients.forEach(c->c.write(message));
        }
    }

}
