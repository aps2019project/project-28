package Controller.menu;

import Controller.Game;
import Controller.GraphicsControlls;
import View.Listeners.OnLeaderBoardClickedListener;
import Model.account.Account;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class SignInMenu extends Menu {

    private static SignInMenu menu;

    private Account temporaryAccount;
    private ArrayList<OnLeaderBoardClickedListener> leaderBoardPresenters;

    private AnchorPane pane ;
    private TextField usernameInput ;
    private TextField nameInput ;
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



        scene.setUserAgentStylesheet("Controller/menu/Scenes/StyleSheets/SignInMenu.css");
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

        GraphicsControlls.setButtonPressedStyles(signInButton , "button1clicked");
        GraphicsControlls.setButtonPressedStyles(signUpButton , "button2clicked");

        signInButton.setOnAction(e -> signInButtonClicked());
        signUpButton.setOnAction(e -> signUpButtonClicked());


        pane.setMinHeight(bounds.getHeight());
        pane.setMinWidth(bounds.getWidth());


    }

    private void signUpButtonClicked() {
        if (nameInput == null) {
            nameInput = new TextField();
            nameInput.setPromptText("Name");
            nameInput.setMinHeight(usernameInput.getHeight());
            nameInput.setFont(new Font("Songti SC Bold", 27));
            frame.getChildren().add(0, nameInput);
        }
        else {
            if (usernameInput.getText() != null && !usernameInput.getText().isEmpty()) {
                if (passwordField.getText() != null && !passwordField.getText().isEmpty()) {
                    if (nameInput.getText() != null && !nameInput.getText().isEmpty()) {

                        try {
                            creatAccount(nameInput.getText(), usernameInput.getText(), passwordField.getText());
                        } catch (AccountAlreadyExistsException e) {
                            usernameInput.setText("");
                            usernameInput.setPromptText("this username already exists !");
                        }

                    } else nameInput.getStyleClass().add("wrong-text-field");
                } else passwordField.getStyleClass().add("wrong-text-field");
            } else usernameInput.getStyleClass().add("wrong-text-field");
        }
    }

    private void signInButtonClicked() {
        if (usernameInput.getText()!=null && !usernameInput.getText().isEmpty()){
            if (passwordField.getText()!=null && !passwordField.getText().isEmpty()){
                try {
                    logIn(usernameInput.getText() , passwordField.getText());
                }catch(InvalidAccountException e1){
                    usernameInput.setText("");
                    passwordField.setText("");
                    usernameInput.setPromptText("incorrect username");
                }catch (WrongPassException e2){
                    passwordField.setText("");
                    passwordField.setPromptText("wrong password");
                }
            } else passwordField.getStyleClass().add("wrong-text-field");
        } else usernameInput.getStyleClass().add("wrong-text-field");

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
