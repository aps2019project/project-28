package Controller.menu;

import Controller.Game;
import Model.account.Account;

public class MultiPlayerModeMenu extends Menu {
    private static MultiPlayerModeMenu menu;
    private MultiPlayerModeMenu(String name) {
        super(name);
    }

    public static MultiPlayerModeMenu getMenu(){
        if(MultiPlayerModeMenu.menu==null){
            MultiPlayerModeMenu.menu=new MultiPlayerModeMenu("MultiPlayerModeMenu");
        }
        return menu;
    }

    public void selectUser(int i) {
        Game.accounts[1] = Account.getAccounts().get(i);
        Game.battle.setPlayer(Game.accounts[0].getPlayer(), Game.accounts[1].getPlayer());
        // TODO: 5/6/19 go to battle automathicaly
    }
}
