package Model.mediator;

import Controller.Game;
import Controller.menu.SignInMenu;
import Model.account.Account;
import exeption.InvalidAccountException;
import network.Message;

import java.util.ArrayList;

public class OnlineAccountMediator implements AccountMediator {
    @Override
    public boolean addNewAccount(Account account) throws Exception {
        Message message = new Message("createAccount");
        message.addCarry(account.getName());
        message.addCarry(account.getUsername());
        message.addCarry(account.getPassword());

        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();
        return true;

    }

    @Override
    public void save() throws Exception {
        Message message=new Message("save");
        message.addCarry(SignInMenu.getMenu().getAccount());
        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

    }

    @Override
    public Account getAccount(String username) throws Exception {
        Message message=new Message("getAccount : username");
        message.addCarry(username);
        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

        return (Account) message.getCarry().get(0);
    }

    @Override
    public Account getAccount(int ID) throws Exception {
        Message message=new Message("getAccount : id");
        message.addCarry(ID);
        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

        return (Account) message.getCarry().get(0);

    }

    @Override
    public ArrayList<Account> getAccounts() throws Exception {
        Message message=new Message("getAccounts");
        Game.getClient().write(message);

        message = Game.getClient().read();
        if (!NetworkMediator.isValid(message)) throw new Exception();

        return (ArrayList<Account>) message.getCarry().get(0);
    }


}
