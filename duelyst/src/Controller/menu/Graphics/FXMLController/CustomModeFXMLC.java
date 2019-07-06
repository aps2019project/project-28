package Controller.menu.Graphics.FXMLController;

import Controller.GameMode.ClassicMode;
import Controller.GameMode.Domination;
import Controller.GameMode.FlagMode;
import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class CustomModeFXMLC extends FXMLController {

    @FXML
    private Button backButton;
    @FXML
    private Button showDeckSelectorButton;

    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/CustomModeMenu.css");

        GraphicsControls.setBackButtonOnPress(backButton);
        GraphicsControls.setButtonStyle("menu-button" , showDeckSelectorButton);
        showDeckSelectorButton.setMinWidth(500);
        showDeckSelectorButton.setPrefWidth(500);
        showDeckSelectorButton.setOnAction(e -> ((CustomModeMenu)menu).showDeckSelector(menu.getAccount()));
        showDeckSelectorButton.setLayoutX(menu.getGraphic().getBounds().getWidth()/2 - showDeckSelectorButton.getWidth());
        showDeckSelectorButton.setLayoutY(menu.getGraphic().getBounds().getHeight()/2 - showDeckSelectorButton.getHeight());

    }
}
