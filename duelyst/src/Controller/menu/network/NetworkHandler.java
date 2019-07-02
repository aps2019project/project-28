package Controller.menu.network;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Map;
import Model.account.Account;
import network.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandler {

    Socket socket;

    public NetworkHandler(Socket socket) {
        this.socket = socket;
    }

    public NetworkHandler() {

    }

    public void handleInsert(int cardID, int x, int y) {
        Message message=new Message("insert "+cardID+ " in "+x+" "+y);
        System.out.println(message.getText());
        System.out.println(Battle.getMenu().allowsCommand(message.getText()));

        this.send(message);
    }

    public void handleSelect(int id) {
        Message message=new Message("select "+id);
        System.out.println(message.getText());
        System.out.println(Battle.getMenu().allowsCommand(message.getText()));

        this.send(message);
    }

    public void handleMove(int x, int y) {
        Message message=new Message("move to "+x+" "+y);
        System.out.println(message.getText());
        System.out.println(Battle.getMenu().allowsCommand(message.getText()));

        this.send(message);

    }

    public void handleAttack(int cardID) {
        Message message=new Message("attack "+cardID);
        System.out.println(message.getText());
        System.out.println(Battle.getMenu().allowsCommand(message.getText()));

        this.send(message);

    }

    public void handleattackCombo(int enemyCardId, int[] troopsId) {
    }

    public void handleSpecialPower(int x, int y) {
        Message message=new Message("use Spacial Power "+x+" "+y);
        System.out.println(message.getText());
        System.out.println(Battle.getMenu().allowsCommand(message.getText()));

        this.send(message);

    }

    public void handleEndTurn() {
        Message message=new Message("end turn");
        System.out.println(message.getText());
        System.out.println(Battle.getMenu().allowsCommand(message.getText()));

        this.send(message);

    }

    public Map getMap() {
        return null;
    }

    public void handleUseItem(int x, int y) {
        Message message=new Message("use "+x+" "+y);
        System.out.println(message.getText());
        System.out.println(Battle.getMenu().allowsCommand(message.getText()));

        this.send(message);

    }

    public void handleInit() {
        Message message=new Message("System Command:"+"init");
        System.out.println(message.getText());
        System.out.println(Battle.getMenu().allowsCommand(message.getText()));

        this.send(message);

        try (ObjectInputStream in=new ObjectInputStream(socket.getInputStream())){
            Game.accounts[1]= (Account) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void send(Message message) {
        try(ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream())){
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
