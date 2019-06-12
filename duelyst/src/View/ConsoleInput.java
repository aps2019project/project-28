package View;



import Controller.Game;
import Controller.menu.Battle;
import Model.account.Deck;

import java.util.Scanner;

public class ConsoleInput {
    protected ConsoleOutput consoleOutput =new ConsoleOutput();

    public void play(){
        Scanner commands= MenuHandler.getGameScanner();
        MenuHandler.showMenu();
        String command;
        while(commands.hasNext()){
            try {
                System.out.println("Game.accounts[0].getCollection().getMainDeck().getHero().getGraphics() = " + Game.accounts[0].getCollection().getMainDeck().getHero().getGraphics());
                System.out.println("Game.accounts[1].getCollection().getMainDeck().getHero().getGraphics() = " + Game.accounts[1].getCollection().getMainDeck().getHero().getGraphics());
                System.err.println("heyyyyyy sexy lady");
                System.err.println(Battle.getMenu().getPlayer().getDeck().getHero().getBuffEffects().allowsAttack()+"allows attack");
            }catch (Exception e){}
            System.err.println(Game.accounts[0].getPlayer().getClass());
            System.err.println(Game.accounts[1].getPlayer().getClass());
            command = commands.nextLine().toLowerCase().trim();

            consoleOutput.handleCommand(command);

            MenuHandler.showMenu();
            MenuHandler.nextMove();

            commands=MenuHandler.getGameScanner();
        }
    }

}
