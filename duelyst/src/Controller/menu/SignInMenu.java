package Controller.menu;

import Controller.Game;
import View.Listeners.OnLeaderBoardClickedListener;
import Model.account.Account;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

import java.util.ArrayList;

public class SignInMenu extends Menu{

    private Account temporaryAccount;
    private ArrayList<OnLeaderBoardClickedListener> leaderBoardPresenters;

    public void creatAccount (String name,String username,String password) throws AccountAlreadyExistsException {
        if(Account.hasAccount(username))
            throw new AccountAlreadyExistsException();
        temporaryAccount = new Account(name,username,password);
    }

    public void logIn(String username,String password) throws InvalidAccountException, WrongPassException {
            Account account = Account.getAccount(username);
            if(account.getPassword().equals(password)) {
                Game.accounts[0] = account;
                Game.hasLoggedIn = true;
            }
            else {
                throw new WrongPassException();
            }
    }

    public void logOut(){
        Game.hasLoggedIn = false;
        Game.accounts[0] = null;
    }

    public void save(){
        Account.addNewAccount(temporaryAccount);
        temporaryAccount = null;
    }

    public void showLeaderBoard(){
        for (OnLeaderBoardClickedListener presenter : this.leaderBoardPresenters) {
            presenter.show();
        }
    }

    public SignInMenu(Menu parentMenu, String name) {
        super(parentMenu, name);
        this.account=parentMenu.getAccount();
        this.leaderBoardPresenters=new ArrayList<>();
    }

    public void addLeaderBoardClickedListener(OnLeaderBoardClickedListener presenter){
        this.leaderBoardPresenters.add(presenter);
    }

    @Override
    public void help() {
        // TODO: 4/30/19 HELPPPPPP!!!!!
    }
}
