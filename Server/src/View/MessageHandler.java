package View;

import Controller.menu.SignInMenu;
import Model.account.Account;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import network.Auth;
import network.Message;

import java.util.ArrayList;

public class MessageHandler {
    public Message handleMessage(Message message){
        String menu =message.getMenu();
        if(menu.equals(SignInMenu.getMenu().getName())){
            return SignInMenuCommandHandler(message);
        }
        return null;
    }



    private static Message SignInMenuCommandHandler(Message message) {
        SignInMenu menu= SignInMenu.getMenu();
        String text=message.getText();
        ArrayList<Object> carry = message.getCarry();

        Message respond = Message.getFailedMessage();
        System.out.println("text = " + text);
        System.err.println("debug");
        if(text.equals("createAccount")){
            try {
                Account.addNewAccount((String) carry.get(0),(String)carry.get(1), (String) carry.get(2));
                respond=Message.getDoneMessage();
                respond.setAuth(new Auth());
            } catch (AccountAlreadyExistsException e) {
                respond = makeExceptionMessage(e);
            }
        }else if(text.equals("save")){
            // TODO: 7/2/19 change save implementation
            Account.save();
            respond=Message.getDoneMessage();
        }else if(text.equals("getAccount : username")){
            try {
                Account account = Account.getAccount((String) carry.get(0));
                respond=Message.getDoneMessage();
                respond.addCarry(account);
                return respond;
            } catch (InvalidAccountException e) {
                respond=makeExceptionMessage(e);
            }
        }else if(text.equals("getAccount : id")){
            try {
                Account account = Account.getAccount((Integer) carry.get(0));
                respond=Message.getDoneMessage();
                respond.addCarry(account);
            } catch (InvalidAccountException e) {
                respond=makeExceptionMessage(e);
            }
        }else if(text.equals("getAccounts")){
            System.err.println("understood!");
            ArrayList<Account> accounts = Account.getAccounts();
            System.err.println(accounts.size());
            respond=Message.getDoneMessage();
            respond.addCarry(accounts);
        }
        return respond;
    }

    private static Message makeExceptionMessage(Exception e) {
        Message respond = Message.getFailedMessage();
        respond.addCarry(e);
        return respond;
    }
}
