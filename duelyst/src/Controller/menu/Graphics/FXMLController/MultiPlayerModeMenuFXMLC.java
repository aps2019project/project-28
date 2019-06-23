package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

public class MultiPlayerModeMenuFXMLC extends FXMLController {

    @FXML
    private Button signInButton;
    @FXML
    private Button backButton;




    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/MultiPlayerMenu.css");


        signInButton.setOnAction(e -> signInButtonClicked());
//        backButton.setOnAction(e->{
//
//        });

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER){
                signInButtonClicked();
            }
        });

        GraphicsControls.setButtonStyle( "button1clicked",signInButton);
        GraphicsControls.setButtonStyle( "button2clicked",signInButton);

        signInButton.setOnAction(e -> signInButtonClicked());
    }

    private void signInButtonClicked() {
    }

    private void enterSubMenu(Menu subMenu){
        menu.enter(subMenu);
    }
}
