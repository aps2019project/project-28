package Model.mediator;

import Controller.Game;
import Model.account.Account;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import network.Message;

import java.util.ArrayList;

public class NetAccountMediator implements AccountMediator {
    @Override
    public void addNewAccount(Account account) throws AccountAlreadyExistsException {
        Message message=new Message("create account");
        message.addCarry(account);
        Game.getClient().send(message);
    }

    @Override
    public void save() {

    }

    @Override
    public Account getAccount(String username) throws InvalidAccountException {
        return null;
    }

    @Override
    public Account getAccount(int ID) throws InvalidAccountException {
        return null;
    }

    @Override
    public ArrayList<Account> getAccounts() {
        return null;
    }
}
