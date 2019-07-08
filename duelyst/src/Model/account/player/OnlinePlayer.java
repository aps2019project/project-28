package Model.account.player;

import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.OnlineBattleMediator;
import Model.account.Account;
import network.Message;

import java.io.InputStream;
import java.util.Scanner;

public class OnlinePlayer extends Bot {

    private Scanner in;


    public OnlinePlayer(Account user, int maxMana, int mana, Scanner serverInput) {
        super(user, maxMana, mana);
        System.err.println("debug");
        in=(serverInput);
    }

    public Scanner getInput() {
        return in;
    }

    @Override
    public void doYourMove() {
        super.doYourMove();
    }

    @Override
    protected void play() {
        System.err.println("debug");
        Message message = Game.getBattleClient().read();
        this.output= (String) message.getCarry().get(0);
        System.out.println("---------------------------");
        System.out.println("output :"+output);
        System.out.println("---------------------------");
    }
}
