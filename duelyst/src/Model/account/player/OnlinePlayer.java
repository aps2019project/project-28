package Model.account.player;

import Controller.Game;
import Model.account.Account;
import network.Message;

import java.io.InputStream;
import java.util.Scanner;

public class OnlinePlayer extends Bot {

    private Scanner in;


    public OnlinePlayer(Account user, int maxMana, int mana, Scanner serverInput) {
        super(user, maxMana, mana);
        in=(serverInput);
    }

    public Scanner getInput() {
        return in;
    }

    @Override
    protected void play() {
        Message message = Game.getBattleClient().read();
        this.output= (String) message.getCarry().get(0);
    }
}
