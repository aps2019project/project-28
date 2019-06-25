package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
import View.MenuHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class SinglePlayerModeMenuFXMLC extends FXMLController {

    @FXML
    private Button backButton;
    @FXML
    private Button storyButton;
    @FXML
    private Button customButton;


    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/Menu.css");


        GraphicsControls.setButtonStyle("menu-button" , storyButton , customButton);

        GraphicsControls.setBackButtonOnPress(backButton);

        storyButton.setOnAction(e -> enterSubMenu(StoryModeMenu.getMenu()));
        customButton.setOnAction(e -> enterSubMenu(CustomModeMenu.getMenu()));
    }

    private void enterSubMenu(Menu subMenu){
        MenuHandler.setCurrentMenu(menu.enter(subMenu));
    }
}
