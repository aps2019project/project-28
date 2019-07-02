package network.server;

import Controller.menu.Battle;
import Model.account.Account;
import network.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BattleServer {

    private ServerSocket server;
    private BattleClient[] client =new BattleClient[2];

    public BattleServer() throws IOException {
        this.server = new ServerSocket(0);
    }

    public ServerSocket getServer() {
        return server;
    }
    public int getPort(){
        return this.server.getLocalPort();
    }

    public void run() throws IOException {
        for(int i=0;i<2;i++) {
            client[i].setSocket(server.accept());
            client[i].init();
        }
        for(int i=0;i<2;i++) {

            this.communicate(client[i]);
        }
    }

    private void communicate(BattleClient client) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try(ObjectInputStream in=new ObjectInputStream(client.getInputStream());
                    ObjectOutputStream out=new ObjectOutputStream(client.getOutputStream())) {

                    boolean battleIsGoing=true;

                    while (battleIsGoing){
                        Message message= (Message) in.readObject();
                        handleCommand(client,message.getText());
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleCommand(BattleClient client, String command) {
        if(command.contentEquals("System Command:")){
            this.handleRequest(client,command.substring(command.indexOf("System Command:")+"System Command:".length()));
        }
    }

    private void handleRequest(BattleClient client, String command) {
        if(command.equals("init")){
            this.handleInit(client);
        }
    }

    private void handleInit(BattleClient client) {
        try (ObjectOutputStream out=new ObjectOutputStream(client.getOutputStream())){
            Account first=client.getAccount();
            Account second=getEnemy(client).getAccount();

            out.writeObject(first);
            out.writeObject(second);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BattleClient getEnemy(BattleClient me){
        if(client[0]==me)return client[1];
        return client[0];
    }
}
