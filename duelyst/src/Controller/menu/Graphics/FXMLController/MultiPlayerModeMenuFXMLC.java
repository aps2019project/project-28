package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
import View.MenuHandler;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class MultiPlayerModeMenuFXMLC extends FXMLController implements LeaderBoardHavingFXMLC {

    @FXML
    private Button signInButton;
    @FXML
    private Button showLeaderBoard ;
    @FXML
    private Button backButton;
    @FXML
    private TextField usernameInput ;
    @FXML
    private PasswordField passwordField ;




    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/MultiPlayerMenu.css");


        signInButton.setOnAction(e -> signInButtonClicked());
        GraphicsControls.setBackButtonOnPress(backButton);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER){
                signInButtonClicked();
            }
        });

        GraphicsControls.setButtonStyle( "button1",signInButton);
        GraphicsControls.setButtonStyle( "menu-button",showLeaderBoard);
        showLeaderBoard.setOnAction(e -> ((MultiPlayerModeMenu)menu).showLeaderBoard());

        signInButton.setOnAction(e -> signInButtonClicked());
    }

    private void signInButtonClicked() {
        if (usernameInput.getText()!=null && !usernameInput.getText().isEmpty()){
            if (passwordField.getText()!=null && !passwordField.getText().isEmpty()){
                try {
                    ((MultiPlayerModeMenu)menu).selectUser(usernameInput.getText() , passwordField.getText());
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


    @Override
    public void setUsernameInput(String username) {
        usernameInput.setText(username);
    }
}
