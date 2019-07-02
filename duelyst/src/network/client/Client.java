package network.client;

import Model.account.Account;
import network.Message;

import java.io.*;
import java.net.Socket;

public class Client {

    private static final String HOST="127.0.0.1";
    private static final int DEFAULT_PORT =8000;

    private Socket socket;
    private Account account;
    private Auth authToken;

    public Client() throws IOException {
        this.socket=new Socket(HOST,DEFAULT_PORT);
    }
    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    public Auth getAuthToken() {
        return authToken;
    }

    public void setAuth(Auth authToken) {
        this.authToken = authToken;
    }

    public void send(Message message) {
        message.setAuth(authToken);
        try(ObjectOutputStream out=new ObjectOutputStream(this.getOutputStream())){
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message read() {
        try(ObjectInputStream in=new ObjectInputStream(this.getInputStream())){
            return (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
