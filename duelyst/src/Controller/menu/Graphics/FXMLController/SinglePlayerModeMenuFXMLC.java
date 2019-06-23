package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
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
/*        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/MainMenu.css");


        GraphicsControls.setButtonStyle("menu-button" , storyButton , customButton);


        backButton.setOnAction(e->{
            enterSubMenu(MainMenu.getMenu());
        });
        backButton.setOnMousePressed(e -> {
            backButton.setTranslateX(-5);
            backButton.setTranslateY(-5);
        });
        backButton.setOnMousePressed(e -> {
            backButton.setTranslateX(5);
            backButton.setTranslateY(5);
        });


        storyButton.setOnAction(e -> enterSubMenu(StoryModeMenu.getMenu()));
        customButton.setOnAction(e -> enterSubMenu(CostumeModeMenu.getMenu()));*/
    }

    private void enterSubMenu(Menu subMenu){
        menu.enter(subMenu);
    }
}
