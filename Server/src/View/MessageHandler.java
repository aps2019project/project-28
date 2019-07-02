package View;

import Controller.menu.MainMenu;
import Controller.menu.Menu;
import Controller.menu.SignInMenu;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import network.Auth;
import network.Message;

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
        String[] word=message.getText().toLowerCase().trim().split(" ");
        if(word[0].equals("create") && word[1].equals("account")){
            try {
                menu.creatAccount(word[2],word[3],word[4]);
                Message respond=new Message("+");
                respond.setAuth(new Auth());
                return message;
            } catch (AccountAlreadyExistsException | WrongPassException | InvalidAccountException e) {
                Message respond=new Message("-");
                respond.addCarry(e);
                return message;
            }
        }else if(word[0].equals("login")){
            try {
                menu.logIn(word[1],word[2]);
                MenuHandler.enterMenu(menu.enter(MainMenu.getMenu()));
            } catch (InvalidAccountException e) {
                System.out.println("Account doesnt exist");
            } catch (WrongPassException e) {
                System.out.println("username and password doesnt match try again");
            } catch(ArrayIndexOutOfBoundsException e){
                System.out.println("please enter in the fallowing order");
                System.out.println("1)username      2)password");
            }
        }else if(word[0].equals("show") && word[1].equals("leaderboard")){
            menu.showLeaderBoard();

        }else if(word[0].equals("save")){
            menu.save();
        }else if(word[0].equals("logout")){
            menu.logOut();
        }
        return null;
    }
}
