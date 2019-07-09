package Model.mediator;

import Controller.Game;
import Model.account.Account;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

public class OfflineMultiPlayerMenuMediator implements MultiPlayerMenuMediator {

    @Override
    public void selectUser(String username, String password) throws InvalidAccountException, WrongPassException {

        Account account = Account.getAccount(username);
        if (!account.getPassword().equals(password)) {
            throw new WrongPassException();
        }
        Game.setSecondAccount(account);
    }
}
