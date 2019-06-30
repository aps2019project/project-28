package View;



import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.Menu;
import Model.account.player.CGI;
import Model.account.player.GGI;

import java.util.Scanner;

public class ConsoleView implements View{
    @Deprecated
    protected CommandHandler commandHandler;
    protected ConsoleOutput consoleOutput=ConsoleOutput.getInstance();
    @Override
    public void play(String... args) {
        setGIs();
        System.err.println("debug");
        MenuHandler.startMenus();
        MenuHandler.showMenu();


        while(true){
            MenuHandler.showMenu();
            MenuHandler.nextMove();
        }

    }

    @Override
    public void setGIs() {
        Game.setBotGI(CGI.class);
        Game.setUserGI(CGI.class);
        Game.setDefaultGI(CGI.class);
    }
}
