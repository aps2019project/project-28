package network.server;

import Controller.Game;
import Controller.GameMode.GameMode;
import Controller.menu.Battle;
import Controller.menu.MainMenu;
import Model.account.player.AI;
import exeption.*;
import network.Message;
import network.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public class BattleServer {
    Client[] client = new Client[2];

    ServerSocket socket;
    GameMode gameMode;

    public BattleServer(Client client1, Client client2, GameMode gameMode) throws IOException {
        socket = new ServerSocket(0);
        this.client[0] = client1;
        this.client[1] = client2;
        this.gameMode = gameMode;
    }


    public void startBattle() {
        System.err.println();
        setAccounts();
        initBattleMenu();
        sendMap();
        while (true) {
                                                                                                                        System.err.println("debug");
                                                                                                                        System.out.println("turn() = " + Battle.getMenu().getTurn());
                                                                                                                        System.out.println("Username = " + client[Battle.getMenu().getTurn()].getAccount().getUsername());
            Message message = client[Battle.getMenu().getTurn()].read();
                                                                                                                        System.out.println("message = " + message.getText());
                                                                                                                        System.out.println("carry = " + message.getCarry().get(0));
            if (message.getText().equals("playerMove")) {
                                                                                                                        System.out.println("fucking next turn = " + (Battle.getMenu().getTurn() + 1) % 2);
                client[(1-Battle.getMenu().getTurn())].write(message);
                handleCommand(message);
                Battle.getMenu().showMenu();
            }else if(message.getText().equals("finish")){
                Battle.getMenu().handleBattleFinish();

            }
        }
    }






















    private void sendMap() {
            System.out.println("client[0].getAccount().getUsername() = " + client[0].getAccount().getUsername());
            Message message = client[0].read();
            System.out.println("message.getText() = " + message.getText());
            if (message.getText().equals("init")) {
                message = new Message("init");
                message.addCarry(Battle.getMenu().getMap());
                client[0].write(message);
            }
            System.out.println("client[1].getAccount().getUsername() = " + client[1].getAccount().getUsername());
            message = client[1].read();
            System.out.println("message.getText() = " + message.getText());
            if (message.getText().equals("init")) {
                message = new Message("init");
                message.addCarry(Battle.getMenu().getMap());
                client[1].write(message);
            }
    }
    private void handleCommand(Message message) {

        String[] word = ((String) message.getCarry().get(0)).split(" ");
        Battle menu = Battle.getMenu();

        System.err.println("word:");
        for (int i = 0; i < word.length; i++) {
            System.err.print(word[i]);
        }


        if (word[0].equals("select")) {
            try {
                menu.select(Integer.parseInt(word[1]));
            } catch (InvalidCardException e) {
                System.out.println("im afraid that you dont acquire this card");
            } catch (InvalidItemException e) {
                System.out.println("im afraid that you dont acquire this item");
            }
        } else if (word[0].equals("move") && word[1].equals("to")) {
            System.err.println();
            try {
                menu.move(Integer.parseInt(word[2]), Integer.parseInt(word[3]));
            } catch (NoCardHasBeenSelectedException e) {
                System.out.println("please select a card first");
            } catch (CardCantBeMovedException e) {
                System.out.println("this card cant be moved due the spell unleashed upon it");
                System.out.println("Or Maybe he/she is just a little bit tired :D");
            } catch (MoveTrunIsOverException e) {
                System.out.println("there is no time to move !!!");
                System.out.println("Attack attack attack");
            } catch (DestinationOutOfreachException e) {
                System.out.println("Ooooo to far!");
                System.out.println("please set the destination some were close");
            } catch (InvalidCellException e) {
                System.out.println("Im afraid our little word doesnt have enough space for your ambitions");
            } catch (DestinationIsFullException e) {
                System.out.println("SomeBodys already there ");
                System.out.println("another location maybe?");
            }
        } else if (word[0].equals("attack")) {
            try {
                menu.attack(Integer.parseInt(word[1]));
            } catch (NoCardHasBeenSelectedException e) {
                System.out.println("please select a card first");
            } catch (InvalidCardException e) {
                System.out.println("are you sure?");
                System.out.println("cause it seems like our enemy doesnt have such card on the ground");
            } catch (DestinationOutOfreachException e) {
                System.out.println("marchin on this destinations may not result in our benefit");
                System.out.println("my lord.... please reconsider");
            } catch (CantAttackException e) {
                System.out.println("this card cant attack due the spell unleashed upon it");
            } catch (InvalidCellException e) {
                System.out.println("Im afraid our little word doesnt have enough space for your ambitions");
            }
        } else if (word[0].equals("use") && word[1].equals("special") && word[2].equals("power")) {
            try {
                menu.useSpecialPower(Integer.parseInt(word[3]), Integer.parseInt(word[4]));
            } catch (InvalidCellException e) {
                System.out.println("sorry but you have to pick a different cell");
            } catch (InvalidCardException e) {
                System.out.println("sorry ! invalid card");
            } catch (CantSpecialPowerCooldownException e) {
                System.out.println("sorry but you have to cool down first man !");
            }
        } else if (word[0].equals("insert")) {
            try {
                menu.insert(Integer.parseInt(word[1]), Integer.parseInt(word[3]), Integer.parseInt(word[4]));
            } catch (InvalidCardException e) {
                System.out.println("thre is no such card in your hand");
            } catch (NotEnoughManaException e) {
                System.out.println("lets collect some mana first!");
            } catch (DestinationIsFullException e) {
                System.out.println("cant spwan/deploy card on the selected destination");
            } catch (InvalidCellException e) {
                System.out.println("Im afraid our little word doesnt have enough space for your ambitions");
            }
        } else if (word[0].equals("use")) {
            try {
                menu.useItem(Integer.parseInt(word[1]), Integer.parseInt(word[2]));
            } catch (InvalidCellException e) {
                System.out.println("cell " + Integer.parseInt(word[1]) + " , " + Integer.parseInt(word[2]) + "says: ");
                System.out.println("cant touch this!");
            } catch (NoItemHasBeenSelectedException e) {
                System.out.println("please select an item first");
            }
        } else if (word[0].equals("end") && word[1].equals("turn")) {
            try {

                System.err.println("end turn");
                menu.endTurn();
            } catch (HandFullException | DeckIsEmptyException ignored) {
                // :D
            }
        }
    }
    private void initBattleMenu() {

        Game.setFirstAccount(client[0].getAccount());
        Game.setSecondAccount(client[1].getAccount());
        Battle.getMenu().setGameMode(this.gameMode);
        Battle.getMenu().init(MainMenu.getMenu());
        Battle.getMenu().setAccount(Game.getAccount(0));
    }
    private void setAccounts() {
        Message message1 = new Message("server Port");
        message1.addCarry(client[1].getAccount());
        message1.addCarry(0);
        client[0].write(message1);

        Message message2 = new Message("server Port");
        message2.addCarry(client[0].getAccount());
        message2.addCarry(1);
        client[1].write(message2);
    }

}
