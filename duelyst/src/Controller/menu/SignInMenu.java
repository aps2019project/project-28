package Controller.menu;

import Controller.Game;
import View.Listeners.OnLeaderBoardClickedListener;
import Model.account.Account;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.NoAccountHasBeenSignedInException;
import exeption.WrongPassException;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class SignInMenu extends Menu {

    private static SignInMenu menu;

    private Account temporaryAccount;
    private ArrayList<OnLeaderBoardClickedListener> leaderBoardPresenters;

    private SignInMenu(String name) {
        super(name);
        this.leaderBoardPresenters = new ArrayList<>();
        this.rootPath = "Scenes/SignInMenu.fxml";
    }


    @Override
    protected void buildScene() {
        super.buildScene();
        Label label = new Label("fuck yea !");
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
            // TODO: 5/7/19 handleOnAttack it better
            System.out.println("no account has been signed in yet");
            return this;
        }
        return super.enter(subMenu);
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
        this.account=null;
    }

    public void save() {
        Account.addNewAccount(temporaryAccount);
        temporaryAccount = null;
    }

    public void showLeaderBoard() {
        Account.sort();
        for (OnLeaderBoardClickedListener presenter : this.leaderBoardPresenters) {
            presenter.show(Account.getAccounts());
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
