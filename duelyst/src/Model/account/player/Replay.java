package Model.account.player;


import Model.account.Account;
import Model.account.Command;
import Model.account.Match;

import java.util.Scanner;

public class Replay extends Bot {
    private Match match;
    private String output;
    private int last;


    public Replay(Account user, int maxMana, int mana, Match match) {
        super(user, maxMana, mana);
        this.match=match;
    }

    @Override
    protected void play() {
        // TODO: 7/6/19 asdasfsdfasdfa
    }

    public void doYourMove(){
        Command command=match.getNextCommand();
//        Thread.sleep();
    }

    @Override
    public Scanner getInputStream() {
        if (this.inputStream != null && this.inputStream.scanner != null) this.inputStream.scanner.close();

        this.inputStream = new ScannerWrapper();
        this.inputStream.scanner = new Scanner(output);

        return this.inputStream.scanner;
    }
}

