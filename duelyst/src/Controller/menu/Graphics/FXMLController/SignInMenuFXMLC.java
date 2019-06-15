package Controller.menu.Graphics.FXMLController;

import Controller.menu.SignInMenu;
import Controller.menu.Graphics.FXMLController.FXMLController;
import Controller.menu.Menu;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SignInMenuFXMLC extends FXMLController {


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
    private TextField nameInput;

    public SignInMenuFXMLC(Menu menu) {
        super(menu);
    }
    public SignInMenuFXMLC() {
        super();
    }




    public void buildScene() {
        super.buildScene();
        Scene scene=this.menu.getGraphic().getScene();
        Rectangle2D bounds=this.menu.getGraphic().getBounds();
        this.menu.getGraphic().getScene().setOnMouseEntered(e -> this.menu.getGraphic().getScene().setCursor(new ImageCursor(new Image(this.menu.getGraphic().getMousePath()))));

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/SignInMenu.css");
        pane = (AnchorPane) scene.lookup("#pane");

        /*
        * there is no more need for them
        * */
//        usernameInput = (TextField)scene.lookup("#username");
//        passwordField = (PasswordField) scene.lookup("#pass");
//        signInButton = (Button)scene.lookup("#signInButton");
//        signUpButton = (Button)scene.lookup("#signUpButton");
        frame = (VBox) scene.lookup("#frame");
//        {
//            if (pane == null) System.err.println("pane input is null");
//            if (usernameInput == null) System.err.println("username input is null");
//            if (passwordField == null) System.err.println("pass input is null");
//            if (signInButton == null) System.err.println("signInButton is null");
//            if (signUpButton == null) System.err.println("signUpButton is null");
//        } //check if they're found


        // TODO: 6/15/19 bring them back to life after you fixed your FXML
//        GraphicsControlls.setButtonPressedStyles(signInButton , "button1clicked");
//        GraphicsControlls.setButtonPressedStyles(signUpButton , "button2clicked");
//
//        signInButton.setOnAction(e -> signInButtonClicked());
//        signUpButton.setOnAction(e -> signUpButtonClicked());


//        pane.setMinHeight(bounds.getHeight());
//        pane.setMinWidth(bounds.getWidth());


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
                            SignInMenu.getMenu().creatAccount(nameInput.getText(), usernameInput.getText(), passwordField.getText());
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
                    SignInMenu.getMenu().logIn(usernameInput.getText() , passwordField.getText());
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

}
