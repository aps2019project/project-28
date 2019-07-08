package Model.account.player;

import Model.account.Account;

import java.util.Scanner;

public abstract class Bot extends Player {

    public String output;


    public Bot(Account user, int maxMana, int mana) {
        super(user, maxMana, mana);
    }


    protected abstract void play() ;
    @Override
    public void doYourMove() {
        this.play();
    }

    @Override
    public Scanner getInputStream() {
        if (this.inputStream != null && this.inputStream.scanner != null) this.inputStream.scanner.close();

        this.inputStream = new ScannerWrapper();
        this.inputStream.scanner = new Scanner(output);

        return this.inputStream.scanner;
    }
}
