package Controller.menu;

import Controller.Game;
import View.Listeners.OnLeaderBoardClickedListener;
import Model.account.Account;
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

    public void creatAccount(String name, String username, String password) throws AccountAlreadyExistsException {
        if (Account.hasAccount(username))
            throw new AccountAlreadyExistsException();
        temporaryAccount = new Account(name, username, password);
    }

    public void logIn(String username, String password) throws InvalidAccountException, WrongPassException {
        Account account = Account.getAccount(username);
        if (account.getPassword().equals(password)) {
            Game.accounts[0] = account;
            Game.hasLoggedIn = true;
            this.account=account;
        } else {
            throw new WrongPassException();
        }
    }

    public void logOut() {
        Game.hasLoggedIn = false;
        Game.accounts[0] = null;
        this.account=null;
    }

    public void save() {
        Account.addNewAccount(temporaryAccount);
        temporaryAccount = null;
    }

    public void showLeaderBoard() {
        for (OnLeaderBoardClickedListener presenter : this.leaderBoardPresenters) {
            presenter.show(Account.getAccounts());
        }
    }

    public void addLeaderBoardClickedListener(OnLeaderBoardClickedListener presenter) {
        this.leaderBoardPresenters.add(presenter);
    }

    @Override
    public void help() {
        // TODO: 4/30/19 HELPPPPPP!!!!!
    }
}
