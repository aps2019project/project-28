package View;

import Controller.menu.ShopMenu;
import Controller.menu.SignInMenu;
import Model.account.Account;
import Model.account.Shop;
import exeption.*;
import network.Auth;
import network.Message;

import java.util.ArrayList;

public class MessageHandler {
    public Message handleMessage(Message message){
        if(message.getAuthToken()!=null && !message.getAuthToken().authenticate())return Message.getFailedMessage();
        System.out.println("message = " + message.getText());

        String text=message.getText();
        ArrayList<Object> carry = message.getCarry();

        Message respond = Message.getFailedMessage();
        System.out.println("text = " + text);
        switch (text) {

            //account requests
            case "createAccount":
                try {
                    Account.addNewAccount((String) carry.get(0), (String) carry.get(1), (String) carry.get(2));
                    respond = Message.getDoneMessage();
                } catch (AccountAlreadyExistsException e) {
                    respond = makeExceptionMessage(e);
                }
                break;
            case "save":
                Account.save((Account) carry.get(0));
                respond = Message.getDoneMessage();
                break;
            case "getAccount : username":
                try {
                    Account account = Account.getAccount((String) carry.get(0));
                    respond = Message.getDoneMessage();
                    respond.addCarry(account);
                    return respond;
                } catch (InvalidAccountException e) {
                    respond = makeExceptionMessage(e);
                }
                break;
            case "getAccount : id":
                try {
                    Account account = Account.getAccount((Integer) carry.get(0));
                    respond = Message.getDoneMessage();
                    respond.addCarry(account);
                } catch (InvalidAccountException e) {
                    respond = makeExceptionMessage(e);
                }
                break;
            case "getAccounts":
                System.err.println("understood!");
                ArrayList<Account> accounts = Account.getAccounts();
                System.err.println(accounts.size());
                respond = Message.getDoneMessage();
                respond.addCarry(accounts);
                break;
            //SignInMenu requests
            case "Log in":
                try {
                    SignInMenu.getMenu().logIn((String) carry.get(0), (String) carry.get(1));
                    Account account = Account.getAccount((String) carry.get(0));
                    respond = Message.getDoneMessage();
                    respond.addCarry(account);
                    respond.addCarry(Auth.generateAuth((String) carry.get(0)));
                } catch (InvalidAccountException | WrongPassException e) {
                    respond = makeExceptionMessage(e);
                }
                break;

            //shop requests:
            case "has card":
                respond=Message.getDoneMessage();
                respond.addCarry(Shop.getInstance().hasCard((String) carry.get(0)));
                break;
            case "has item":
                respond=Message.getDoneMessage();
                respond.addCarry(Shop.getInstance().hasItem((String) carry.get(0)));
                break;
            case "get card":
                try {
                    respond=Message.getDoneMessage();
                    respond.addCarry(Shop.getInstance().getCard((String) carry.get(0)));
                } catch (InvalidCardException e) {
                    respond=makeExceptionMessage(e);
                }
                break;
            case "get item":
                try {
                    respond=Message.getDoneMessage();
                    respond.addCarry(Shop.getInstance().getItem((String) carry.get(0)));
                } catch (InvalidItemException e) {
                    respond=makeExceptionMessage(e);
                }
                break;
            case "buy":
                respond=Message.getDoneMessage();
                respond.addCarry(Shop.getInstance().buy((String) carry.get(0)));
                break;
            case "sell":
                try {
                    respond=Message.getDoneMessage();
                    Shop.getInstance().sell((String) carry.get(0));
                } catch (InvalidCardException | InvalidItemException e) {
                    respond=makeExceptionMessage(e);
                }
                break;
            case "get collection":
                respond=Message.getDoneMessage();
                respond.addCarry(Shop.getInstance().getCollection());
                break;
            case "get Remain":
                respond=Message.getDoneMessage();
                respond.addCarry(Shop.getInstance().getRemain((String) carry.get(0)));
        }
        return respond;
    }

    private static Message makeExceptionMessage(Exception e) {
        Message respond = Message.getFailedMessage();
        respond.addCarry(e);
        return respond;
    }
}
