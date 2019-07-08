package Controller.menu;

import Controller.Game;
import Controller.menu.Graphics.FXMLController.CollectionMenuFXMLC;
import Controller.menu.Graphics.FXMLController.DeckSelector2FXMLC;
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
        this.rootPath = "Scenes/SignInMenu.fxml";
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

    public void creatAccount(String name, String username, String password) throws AccountAlreadyExistsException {
        if (Account.hasAccount(username))
            throw new AccountAlreadyExistsException();
        temporaryAccount = new Account(name, username, password);
        save() ;
        try {
            logIn(username , password);
        } catch (InvalidAccountException e) {
            System.err.println("InvalidAccount after creating an account and then trying to login ! \n " +
                    "signInMenu : 137");
        } catch (WrongPassException e) {
            System.err.println("WrongPassword after creating an account and then trying to login ! \n " +
                    "signInMenu : 137");
        }
    }

    public void logIn(String username, String password) throws InvalidAccountException, WrongPassException {
        Account account = Account.getAccount(username);
        if (account.getPassword().equals(password)) {
            Game.accounts[0] = account;
            Game.hasLoggedIn = true;
            this.account=account;
            MenuHandler.setAccount(account);
            // TODO: 6/30/19 in ro azin ja bardar or not

            MenuHandler.enterMenu(menu.enter(MainMenu.getMenu()));
            new Thread("initializing shop and collection"){
                @Override
                public void run() {
                    ShopMenu.getMenu().getGraphic().init();
                    CollectionMenu.getMenu().getGraphic().init();
                    ((CollectionMenuFXMLC)CollectionMenu.getMenu().getGraphic().getController())
                            .setDeckSelector2Listener(DeckSelector2FXMLC::makeNewScene);
                }
            }.start();
            MenuHandler.enterMenu(MainMenu.getMenu());
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
        Account.addNewAccount(temporaryAccount);
        temporaryAccount = null;
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
