package Controller.menu.Graphics.FXMLController;

import Controller.GameMode.ClassicMode;
import Controller.GameMode.Domination;
import Controller.GameMode.FlagMode;
import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
import View.MenuHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class CraftingMenuFXMLC extends FXMLController {

    @FXML
    private Button backButton;
    @FXML
    private Button hero;
    @FXML
    private Button spell;
    @FXML
    private Button minion;




    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/Menu.css");


        GraphicsControls.setButtonStyle("menu-button" , hero, spell, minion);

        GraphicsControls.setBackButtonOnPress(backButton);


        hero.setOnAction(e -> {
            MenuHandler.setCurrentMenu(CraftingHeroMenu.getMenu());
        });
        spell.setOnAction(e -> {
            MenuHandler.setCurrentMenu(CraftingSpellMenu.getMenu());
        });
        minion.setOnAction(e -> {
            MenuHandler.setCurrentMenu(CraftingMinionMenu.getMenu());
        });


    }
}
