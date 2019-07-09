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

    private static final int SEND_SIZE=200;
    private static final String END_MESSAGE = "ARSHIA_FATTEME_SAEE";

    private Socket socket;
    private Account account;
    private Auth authToken;

    private PrintStream out;
    private Scanner in;

    public Client() throws IOException {
        this.socket = new Socket(HOST, DEFAULT_PORT);
    }
    public Client(int port) throws IOException {
        this.socket = new Socket(HOST, port);
    }
    public Client(Socket socket) {
        this.socket = socket;
    }
    public Client(Account account) {
        this.account = account;
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
        System.out.println("message = " + message.getText());
        message.setAuth(authToken);
        message.setMenu(MenuHandler.getCurrentMenu());
        YaGson json = new YaGson();
        String string = json.toJson(message);
        System.out.println("_____________________________________");
        System.out.println("string.length() = " + string.length());
        try {
            for(int i=0;i<= string.length()/SEND_SIZE;i++){
                String sendable=string.substring(i*SEND_SIZE,Integer.min((i+1)*SEND_SIZE,string.length()));
                this.getOutput().println(sendable);
            }
            this.getOutput().println(END_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Message read() {
        YaGson json = new YaGson();
        try {
            StringBuilder readable=new StringBuilder();
            while(true){
                String s=this.getInput().nextLine();
                if(s.equals(END_MESSAGE))break;
                readable.append(s);
            }
            return json.fromJson(readable.toString(), Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void sendTaskDone(){
        Message message=Message.getDoneMessage();
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
    public void sendTaskFailed(){
        Message message=Message.getFailedMessage();
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

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
