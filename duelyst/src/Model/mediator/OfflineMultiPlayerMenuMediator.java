package Model.mediator;

import Controller.Game;
import Model.account.Account;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import network.ChatMSG;

import java.util.ArrayList;

public class OfflineMultiPlayerMenuMediator implements MultiPlayerMenuMediator {

    @Override
    public void selectUser(String username, String password) throws InvalidAccountException, WrongPassException {

        Account account = Account.getAccount(username);
        if (!account.getPassword().equals(password)) {
            throw new WrongPassException();
        }
        Game.setSecondAccount(account);
    }

    @Override
    public void sendMessage(String text) {

    }

    @Override
    public ArrayList<ChatMSG> getChats() {
        return null;
    }
}
