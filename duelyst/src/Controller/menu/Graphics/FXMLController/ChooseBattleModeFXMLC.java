package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControlls;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class ChooseBattleModeFXMLC extends FXMLController {

    @FXML
    private Button backButton;
    @FXML
    private Button singlePlayer;
    @FXML
    private Button multiPlayer;




    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/MainMenu.css");


        GraphicsControlls.setButtonStyle("menu-button" , singlePlayer , multiPlayer);


        backButton.setOnAction(e->{
            enterSubMenu(MainMenu.getMenu());
        });
        GraphicsControlls.setBackButtonOnPress(backButton);


        multiPlayer.setOnAction(e -> enterSubMenu(MultiPlayerModeMenu.getMenu()));
        singlePlayer.setOnAction(e -> enterSubMenu(SinglePlayerModeMenu.getMenu()));
    }

    private void enterSubMenu(Menu subMenu){
        menu.enter(subMenu);
    }
}
