package network.client;

import Model.account.Account;
import network.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private static final String HOST="127.0.0.1";
    private static final int DEFAULT_PORT =8000;

    private Socket socket;
    private Account account;

    public Client() throws IOException {
        this.socket=new Socket(HOST,DEFAULT_PORT);
    }
    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    public void send(Message message) {
        try(ObjectOutputStream out=new ObjectOutputStream(this.getOutputStream())){
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
