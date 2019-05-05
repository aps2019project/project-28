package Controller.menu;

import Controller.Game;
import Model.account.Account;

public class MultiPlayerModeMenu extends Menu {
    public MultiPlayerModeMenu(Menu parentMenu) {
        super(parentMenu);
    }

    public void selectUser(int i){
        Game.accounts[1]= Account.getAccounts().get(i);
    }

    @Override
    public void help() {

    }
}
