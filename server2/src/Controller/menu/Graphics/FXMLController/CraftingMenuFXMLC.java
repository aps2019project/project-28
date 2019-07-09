package Controller.menu.Graphics.FXMLController;

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
            CraftingMenu.getHeroMenu().getGraphic().init();
            MenuHandler.enterMenu(CraftingMenu.getHeroMenu());
        });
        spell.setOnAction(e -> {
            CraftingMenu.getSpellMenu().getGraphic().init();
            MenuHandler.enterMenu(CraftingMenu.getSpellMenu());
        });
        minion.setOnAction(e -> {
            CraftingMenu.getMinionMenu().getGraphic().init();
            MenuHandler.enterMenu(CraftingMenu.getMinionMenu());
        });

    }
}
