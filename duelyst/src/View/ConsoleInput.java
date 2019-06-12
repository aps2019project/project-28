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
            System.out.println("MenuHandler.currentMenu = " + MenuHandler.currentMenu);
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
