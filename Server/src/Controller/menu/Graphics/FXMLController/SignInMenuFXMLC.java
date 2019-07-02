package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.SignInMenu;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SignInMenuFXMLC extends FXMLController implements LeaderBoardHavingFXMLC{


    @FXML
    private AnchorPane pane;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private VBox frame;
    @FXML
    private Button showLeaderBoard ;

    private TextField nameInput;


    @Override
    public void init() {
        super.init();
    }

    @Override
    public  void buildScene() {
        super.buildScene();

        Scene scene = this.menu.getGraphic().getScene();
        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/SignInMenu.css");

        signInButton.setOnAction(e -> signInButtonClicked());
        signUpButton.setOnAction(e -> signUpButtonClicked());
        showLeaderBoard.setOnAction(e -> ((SignInMenu)menu).showLeaderBoard());

                        //todo
                        usernameInput.setText("warlord");
                        passwordField.setText("1");

        Rectangle2D bounds=this.menu.getGraphic().getBounds();
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER){
                if (nameInput == null)
                    signInButtonClicked();
                else
                    signUpButtonClicked();
            }
        });

        GraphicsControls.setButtonStyle( "button1",signInButton);
        GraphicsControls.setButtonStyle( "button2",signUpButton);
        GraphicsControls.setButtonStyle("menu-button" , showLeaderBoard);

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
                            ((SignInMenu)menu).creatAccount(nameInput.getText(), usernameInput.getText(), passwordField.getText());
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
                    ((SignInMenu)menu).logIn(usernameInput.getText() , passwordField.getText());
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

    public void setUsernameInput(String username){
        this.usernameInput.setText(username);
    }
}
