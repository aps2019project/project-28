package network.client;

import Controller.Game;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private static final String HOST="127.0.0.1";
    private static final int DEFAULT_PORT =8000;

    public static void main(String[] args) throws IOException {
        Game.client=new Socket(HOST, DEFAULT_PORT);

    }
}
