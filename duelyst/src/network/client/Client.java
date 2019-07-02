package network.client;

import Model.account.Account;
import View.MenuHandler;
import com.gilecode.yagson.YaGson;
import network.Auth;
import network.Message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 8000;

    private Socket socket;
    private Account account;
    private Auth authToken;

    private PrintStream out;
    private Scanner in;

    public Client() throws IOException {
        this.socket = new Socket(HOST, DEFAULT_PORT);
    }

    public Client(Socket socket) {
        this.socket = socket;
    }

    public Scanner getInput() throws IOException {
        if (in == null) in = new Scanner(socket.getInputStream());
        return in;
    }

    public PrintStream getOutput() throws IOException {
        if (out == null) out = new PrintStream(this.socket.getOutputStream(), true);
        return out;
    }

    public Auth getAuthToken() {
        return authToken;
    }

    public void setAuth(Auth authToken) {
        this.authToken = authToken;
    }

    public void write(Message message) {
        message.setAuth(authToken);
        message.setMenu(MenuHandler.getCurrentMenu());
        YaGson json = new YaGson();
        String string = json.toJson(message);
        try {
            this.getOutput().println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Message read() {
        YaGson json = new YaGson();
        try {
            return json.fromJson(this.getInput().nextLine(), Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
