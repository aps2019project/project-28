package network.server;

import Controller.GameMode.ClassicMode;
import Controller.GameMode.Domination;
import Controller.GameMode.FlagMode;
import Controller.GameMode.GameMode;
import Model.account.Account;
import network.Message;
import network.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BattleServerHandler {
    private BlockingQueue<Client> classicModeClients = new LinkedBlockingQueue<>();
    private BlockingQueue<Client> flagModeClients = new LinkedBlockingQueue<>();
    private BlockingQueue<Client> dominationClients = new LinkedBlockingQueue<>();

    private ServerSocket serverSocket;

    public BattleServerHandler() throws IOException {
        this.serverSocket= new ServerSocket(8888);
    }

    public void init() {
        this.startBattles();
            while (true){
                try {
                    System.out.println("accepting the players");
                    Socket socket=serverSocket.accept();
                    System.out.println("player accepted");
                    new Thread(() -> handleClient(socket)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

    private void handleClient(Socket socket) {
        Client client=new Client(socket);
        Message message = client.read();
        System.out.println("setting the account");
        System.out.println("message.getCarry().get(0).getUsername() = " + ((Account)message.getCarry().get(0)).getUsername());
        client.setAccount((Account) message.getCarry().get(0));
        client.setAuth(message.getAuthToken());
        System.out.println("setting the game mode:");
        System.out.println("(GameMode) message.getCarry().get(1) = " + (GameMode) message.getCarry().get(1));
        GameMode gameMode= (GameMode) message.getCarry().get(1);
        addToWaitList(client,gameMode);
    }

    private void startBattles() {
        new Thread(() -> startBattles(classicModeClients,new ClassicMode())).start();
        new Thread(() -> startBattles(flagModeClients,new FlagMode())).start();
        new Thread(() -> startBattles(dominationClients,new Domination())).start();

    }

    public void addToWaitList(Client client, GameMode gameMode){
        System.out.println("adding to the wait list");
        System.out.println("first attepmpt");
        System.out.println("second attepmpt");
        System.out.println("last attepmpt");
        if(gameMode instanceof ClassicMode)classicModeClients.add(client);
        else if(gameMode instanceof FlagMode)flagModeClients.add(client);
        else if(gameMode instanceof Domination)dominationClients.add(client);
        System.out.println("couldnt add");
    }

    private void startBattles(BlockingQueue<Client> clients, GameMode gameMode){
        while(true){
            if(clients.size()>=2){
                System.out.println("staritng the battle !!!!!!!!");
                Client client1=clients.poll();
                Client client2=clients.poll();
                System.out.println("client1.getAccount().getUsername() = " + client1.getAccount().getUsername());
                System.out.println("client2.getAccount().getUsername() = " + client2.getAccount().getUsername());
                try {
                    System.out.println("making a battle server");
                    BattleServer battleServer= new BattleServer(client1,client2,gameMode);
                    System.out.println("starting the battle server");
                    new Thread(battleServer::startBattle).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
