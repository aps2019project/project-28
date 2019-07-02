package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
import View.MenuHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class ChooseBattleModeMenuFXMLC extends FXMLController {

    @FXML
    private Button backButton;
    @FXML
    private Button classic;
    @FXML
    private Button domination;
    @FXML
    private Button flag;




    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/Menu.css");


        GraphicsControls.setButtonStyle("menu-button" , classic , domination , flag);

        GraphicsControls.setBackButtonOnPress(backButton);


        classic.setOnAction(e -> ((ChooseBattleModeMenu)menu).setMode(1) );
        domination.setOnAction(e ->((ChooseBattleModeMenu)menu).setMode(3));
        flag.setOnAction(e -> ((ChooseBattleModeMenu)menu).setMode(2));
    }

    private void enterSubMenu(Menu subMenu){
        menu.enter(subMenu);
    }
}
