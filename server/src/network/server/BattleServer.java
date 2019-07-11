package network.server;

import Controller.Game;
import Controller.GameMode.GameMode;
import Controller.menu.Battle;
import Controller.menu.MainMenu;
import Controller.menu.Menu;
import Model.account.Command;
import Model.account.player.AI;
import View.CommandHandler;
import View.MenuHandler;
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
    CommandHandler commandHandler=new CommandHandler();

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
            if(Battle.getMenu().getTurn()==1)
                System.err.println();
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
            } catch (InvalidCardException | InvalidItemException ignored) { }
        } else if (word[0].equals("move") && word[1].equals("to")) {
            System.err.println();
            try {
                menu.move(Integer.parseInt(word[2]), Integer.parseInt(word[3]));
            } catch (NoCardHasBeenSelectedException | DestinationIsFullException | CardCantBeMovedException | MoveTrunIsOverException | DestinationOutOfreachException | InvalidCellException ignored) {
            }
        } else if (word[0].equals("attack")) {
            try {
                menu.attack(Integer.parseInt(word[1]));
            } catch (NoCardHasBeenSelectedException e) {
                System.out.println("please select a card first");
            } catch (InvalidCardException | DestinationOutOfreachException | CantAttackException | InvalidCellException ignored) {
            }
        } else if (word[0].equals("use") && word[1].equals("special") && word[2].equals("power")) {
            try {
                menu.useSpecialPower(Integer.parseInt(word[3]), Integer.parseInt(word[4]));
            }catch(InvalidCellException | CantSpecialPowerCooldownException | InvalidCardException ignored){
            }
        } else if (word[0].equals("insert")) {
            try {
                menu.insert(Integer.parseInt(word[1]), Integer.parseInt(word[3]), Integer.parseInt(word[4]));
            } catch (InvalidCardException | NotEnoughManaException | DestinationIsFullException | InvalidCellException ignored) {
            }
        } else if (word[0].equals("use")) {
            try {
                menu.useItem(Integer.parseInt(word[1]), Integer.parseInt(word[2]));
            } catch (InvalidCellException | NoItemHasBeenSelectedException ignored) {
            }
        }else if(word[0].equals("end") && word[1].equals("turn")){
            try {

                System.err.println("end turn");
                menu.endTurn();
            } catch (HandFullException | DeckIsEmptyException ignored) {
            }
        }
    }
    private void initBattleMenu() {
        MenuHandler.getCurrentMenu().enter(Battle.getMenu());
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
