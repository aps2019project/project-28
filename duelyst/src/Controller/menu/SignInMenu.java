package Controller.menu;

import Controller.Game;
import Controller.menu.Graphics.FXMLController.LeaderBoardHavingFXMLC;
import View.Listeners.OnLeaderBoardClickedListener;
import Model.account.Account;
import View.MenuHandler;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

import java.util.ArrayList;

public class SignInMenu extends Menu {

    private static SignInMenu menu;

    private Account temporaryAccount;
    private ArrayList<OnLeaderBoardClickedListener> leaderBoardPresenters;

    private SignInMenu(String name) {
        super(name);
        this.leaderBoardPresenters = new ArrayList<>();
    }


    public static SignInMenu getMenu(){
        if(SignInMenu.menu==null){
            SignInMenu.menu=new SignInMenu("SignInMenu");
        }
        return menu;
    }

    @Override
    public Menu enter(Menu subMenu) {
        if(this.account==null){
            System.out.println("no account has been signed in yet");
            return this;
        }
        return super.enter(subMenu);
    }

    public void creatAccount(String name, String username, String password) throws AccountAlreadyExistsException, InvalidAccountException, WrongPassException {

        if(!Account.addNewAccount(new Account(name, username, password)))return;
//        try {
            logIn(username , password);
//        } catch (InvalidAccountException e) {
//            System.err.println("InvalidAccount after creating an account and then trying to login ! \n " +
//                    "signInMenu : 137");
//        } catch (WrongPassException e) {
//            System.err.println("WrongPassword after creating an account and then trying to login ! \n " +
//                    "signInMenu : 137");
//        }
    }

    public void logIn(String username, String password) throws InvalidAccountException, WrongPassException {
        System.err.println("debug");
        Account account = Account.getAccount(username);
        if (account.getPassword().equals(password)) {
            Game.setFirstAccount(account);
            Game.hasLoggedIn = true;
            this.account=account;
            MenuHandler.setAccount(account);
            // TODO: 6/30/19 in ro azin ja bardar or not

            MenuHandler.enterMenu(menu.enter(MainMenu.getMenu()));
        } else {
            throw new WrongPassException();
        }
    }

    public void logOut() {
        Game.hasLoggedIn = false;
        this.account=null;
        MenuHandler.setAccount(null);
    }

    public void save() {
    }

    public void showLeaderBoard() {
        Account.sort();
        for (OnLeaderBoardClickedListener presenter : this.leaderBoardPresenters) {
            presenter.show(Account.getAccounts() , (LeaderBoardHavingFXMLC) SignInMenu.getMenu().getGraphic().getController());
        }
    }

    public void addLeaderBoardClickedListener(OnLeaderBoardClickedListener presenter) {
        this.leaderBoardPresenters.add(presenter);
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4)create account [name] [user name] [password]     5)login [user name] [password]    6)show leaderboard");
        System.out.println("5)save     6)logout    ");
    }
}
