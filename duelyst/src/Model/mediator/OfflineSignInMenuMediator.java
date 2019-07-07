package Model.mediator;

import Controller.Game;
import Controller.menu.MainMenu;
import Controller.menu.SignInMenu;
import Model.account.Account;
import View.MenuHandler;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

public class OfflineSignInMenuMediator implements SignInMenuMediator {

    @Override
    public void logIn(String username, String password) throws InvalidAccountException, WrongPassException {
        System.err.println("debug");
        Account account = Account.getAccount(username);
        if (account.getPassword().equals(password)) {
            Game.setFirstAccount(account);
            Game.hasLoggedIn = true;
            SignInMenu.getMenu().setAccount(account);
            MenuHandler.setAccount(account);
            // TODO: 6/30/19 in ro azin ja bardar or not
        } else {
            throw new WrongPassException();
        }
    }
}
