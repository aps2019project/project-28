package Controller.menu;

import Controller.Game;
import Model.account.AI;
import Model.account.Account;
import exeption.*;

public class StoryModeMenu extends Menu {
    private static StoryModeMenu menu;

    private StoryModeMenu(String name) {
        super(name);
    }

    public static StoryModeMenu getMenu(){
        if(StoryModeMenu.menu==null){
            StoryModeMenu.menu=new StoryModeMenu("StoryModeMenu");
        }
        return menu;
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4) Level [Level number]");
    }

    public Menu setAI(int level) {
        System.err.println("to vazehan bayad ejra shi");
            Game.accounts[1]= Account.AI[level];
        Game.accounts[1].setPlayer(new AI(Game.accounts[1],2,2,Game.accounts[0].getPlayer()));
        System.err.println("you were right ");
        System.err.println("man vazehan ejra shodam");
        return this.enter(Battle.getMenu());
    }
}
