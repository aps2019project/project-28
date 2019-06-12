package Controller.menu;

import Controller.Game;
import View.Listeners.OnLeaderBoardClickedListener;
import Model.account.Account;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SignInMenu extends Menu {

    private static SignInMenu menu;

    private Account temporaryAccount;
    private ArrayList<OnLeaderBoardClickedListener> leaderBoardPresenters;

    private AnchorPane pane ;
    private TextField usernameInput ;
    private PasswordField passwordField ;
    private Button signUpButton ;
    private Button signInButton ;
    private VBox frame ;

    private SignInMenu(String name) {
        super(name);
        this.rootPath = "Scenes/SignInMenu.fxml";
        this.leaderBoardPresenters = new ArrayList<>();
    }

    @Override
    protected void buildScene() {
        super.buildScene();
        scene.setUserAgentStylesheet("/Controller/menu/Scenes/StyleSheets/SignInMenu.css");
        pane = (AnchorPane) scene.lookup("#pane");
        usernameInput = (TextField)scene.lookup("#username");
        passwordField = (PasswordField) scene.lookup("#pass");
        signInButton = (Button)scene.lookup("#signInButton");
        signUpButton = (Button)scene.lookup("#signUpButton");
        frame = (VBox) scene.lookup("#frame");
        {
            if (pane == null) System.err.println("pane input is null");
            if (usernameInput == null) System.err.println("username input is null");
            if (passwordField == null) System.err.println("pass input is null");
            if (signInButton == null) System.err.println("signInButton is null");
            if (signUpButton == null) System.err.println("signUpButton is null");
        } //check if they're found


        pane.setMinHeight(bounds.getHeight());
        pane.setMinWidth(bounds.getWidth());


//
//        background.fitWidthProperty().bind(stage.widthProperty());
//        background.fitHeightProperty().bind(stage.heightProperty());
//
//        double WIDTH = background.getFitWidth();
//        double HEIGHT = stage.getHeight();
//        Image backImg = new Image("resources/images/signInBackground.jpg" , WIDTH , HEIGHT , false , true) ;
//
//        background.setImage(backImg) ;
//        background.setLayoutX(0);
//        background.setLayoutY(0);
//        //frame :
//        {
//            frame.xProperty().bind(background.fitWidthProperty().multiply(0.5));
//            frame.yProperty().bind(background.fitHeightProperty().divide(2.5));
//            frame.heightProperty().bind(background.fitHeightProperty().divide(2));
//            frame.widthProperty().bind(background.fitWidthProperty().divide(2.5));
//            frame.setId("frame");
//        }
//
//        //inputs
//        {
//            usernameInput.getLayoutX()
//        }
//
//        root.getChildren().addAll(background , frame );

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
