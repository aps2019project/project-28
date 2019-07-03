package View;

import Controller.menu.SignInMenu;
import Model.account.Account;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import network.Auth;
import network.Message;

import java.util.ArrayList;

public class MessageHandler {
    public Message handleMessage(Message message){
        if(message.getAuthToken()!=null && !message.getAuthToken().authenticate())return Message.getFailedMessage();
        String menu =message.getMenu();
        System.out.println("message = " + message.getText());
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
        if(text.equals("createAccount")){
            try {
                Account.addNewAccount((String) carry.get(0),(String)carry.get(1), (String) carry.get(2));
                respond=Message.getDoneMessage();
            } catch (AccountAlreadyExistsException e) {
                respond = makeExceptionMessage(e);
            }
        }else if(text.equals("save")){
            // TODO: 7/2/19 change save implementation
            Account.save((Account)carry.get(0));
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
        }else if(text.equals("Log in")){
            try {
                SignInMenu.getMenu().logIn((String)carry.get(0), (String) carry.get(1));
                Account account=Account.getAccount((String)carry.get(0));
                respond=Message.getDoneMessage();
                respond.addCarry(account);
                respond.addCarry(Auth.generateAuth((String) carry.get(0)));
            } catch (InvalidAccountException | WrongPassException e) {
                respond=makeExceptionMessage(e);
            }
        }
        return respond;
    }

    private static Message makeExceptionMessage(Exception e) {
        Message respond = Message.getFailedMessage();
        respond.addCarry(e);
        return respond;
    }
}
