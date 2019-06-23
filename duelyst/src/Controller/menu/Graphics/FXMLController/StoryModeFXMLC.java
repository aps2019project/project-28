package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class StoryModeFXMLC extends FXMLController {

    @FXML
    private Button backButton;
    @FXML
    private Button mode1;
    @FXML
    private Button mode2;
    @FXML
    private Button mode3;




    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/MainMenu.css");


        GraphicsControls.setButtonStyle("menu-button" , mode1 , mode2 , mode3);


        backButton.setOnAction(e->{
            enterSubMenu(SinglePlayerModeMenu.getMenu());
        });
        GraphicsControls.setBackButtonOnPress(backButton);


        mode1.setOnAction(e -> {
            ((StoryModeMenu)menu).setAI(1);
            ((StoryModeMenu) menu).showDeckSelector(menu.getAccount());
        });
        mode2.setOnAction(e -> {
            ((StoryModeMenu)menu).setAI(2);
            ((StoryModeMenu) menu).showDeckSelector(menu.getAccount());
        });
        mode3.setOnAction(e -> {
            ((StoryModeMenu)menu).setAI(3);
            ((StoryModeMenu) menu).showDeckSelector(menu.getAccount());
        });


    }

    private void enterSubMenu(Menu subMenu){
        menu.enter(subMenu);
    }
}
