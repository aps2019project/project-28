package Controller.menu;

import Controller.Game;
import Model.account.Account;

public class MultiPlayerModeMenu extends Menu {
    public MultiPlayerModeMenu(Menu parentMenu, String name) {
        super(parentMenu, name);
    }

    public void selectUser(int i){
        Game.accounts[1]= Account.getAccounts().get(i);
        Game.battle.setPlayer(Game.accounts[0].getPlayer(),Game.accounts[1].getPlayer());
        // TODO: 5/6/19 go to battle automathicaly
    }

    @Override
    public void help() {

    }
}
