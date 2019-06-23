package Controller.menu;

import Controller.Game;
import Model.account.AI;
import exeption.*;

public class SinglePlayerModeMenu extends Menu {
    private static SinglePlayerModeMenu menu;
    private SinglePlayerModeMenu(String name) {
        super(name);
    }

    public static SinglePlayerModeMenu getMenu(){
        if(SinglePlayerModeMenu.menu==null){
            SinglePlayerModeMenu.menu=new SinglePlayerModeMenu("SinglePlayer");
        }
        return menu;
    }

}

