package Model.account;


import java.util.Scanner;

public class Replay extends Player {
    private Match match;
    private String output;
    private int last;


    public Replay(Account user, int maxMana, int mana,Match match) {
        super(user, maxMana, mana);
        this.match=match;
    }
    public void doYourMove(){
        Command command=match.getNextCommand();
//        Thread.sleep();
    }

    @Override
    public Scanner getOutputStream() {
        if (this.outputStream != null && this.outputStream.scanner != null) this.outputStream.scanner.close();

        this.outputStream = new ScannerWrapper();
        this.outputStream.scanner = new Scanner(output);

        return this.outputStream.scanner;
    }
}

