package Controller.menu;

import Controller.Game;
import Controller.menu.Graphics.FXMLController.LeaderBoardHavingFXMLC;
import Model.mediator.SignInMenuMediator;
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

    private SignInMenuMediator signInMenuMediator;

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
            logIn(username , password);
}

    public void logIn(String username, String password) throws InvalidAccountException, WrongPassException {
        try {
            signInMenuMediator.logIn(username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MenuHandler.enterMenu(SignInMenu.getMenu().enter(MainMenu.getMenu()));
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

    public void setSignInMenuMediator(SignInMenuMediator signInMenuMediator) {
        this.signInMenuMediator = signInMenuMediator;
    }
}
