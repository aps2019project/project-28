package Controller.menu;

import Controller.Game;
import Model.account.Account;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

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

    public void selectUser(String username,String password) throws InvalidAccountException, WrongPassException {
        Account account = Account.getAccount(username);
        if (account.getPassword().equals(password)) {
            Game.accounts[1] = account;
            Game.battle.setPlayer(Game.accounts[0].getPlayer(), Game.accounts[1].getPlayer());

        } else {
            throw new WrongPassException();
        }
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4)select user [username] [password]");
    }
}
