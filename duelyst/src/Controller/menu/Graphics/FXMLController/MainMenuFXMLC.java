package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
import Model.Primary;
import Model.account.Account;
import Model.account.Collection;
import View.MenuHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainMenuFXMLC extends FXMLController {

    @FXML
    private Button battleMenuButton;
    @FXML
    private Button collectionMenuButton;
    @FXML
    private Button shopMenuButton;
    @FXML
    private Button exitMenuButton;
    @FXML
    private Button craftButton , saveButton;




    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/Menu.css");
        exitMenuButton.setStyle("-fx-text-fill: red;");

        GraphicsControls.setButtonStyle("menu-button" , battleMenuButton,
                collectionMenuButton, shopMenuButton, craftButton , exitMenuButton , saveButton);


                exitMenuButton.setOnAction(e->{
                    SignInMenu.getMenu().logOut();
                    enterSubMenu(SignInMenu.getMenu());
                });


        collectionMenuButton.setOnAction(e -> {
            try {
                CollectionMenu.getMenu().getGraphic().getController().buildScene();
            }catch(NullPointerException ignored){
                CollectionMenu.getMenu().getGraphic().init();
                ((CollectionMenuFXMLC)CollectionMenu.getMenu().getGraphic().getController())
                        .setDeckSelector2Listener(DeckSelector2FXMLC::makeNewScene);
                CollectionMenu.getMenu().getGraphic().getController().buildScene();
            }
            enterSubMenu(CollectionMenu.getMenu());
        });
        shopMenuButton.setOnAction(e -> {
            try {
                ShopMenu.getMenu().getGraphic().getController().buildScene();
                enterSubMenu(ShopMenu.getMenu());
            }catch(NullPointerException ignored){
                ShopMenu.getMenu().getGraphic().init();
                ShopMenu.getMenu().getGraphic().getController().buildScene();
                enterSubMenu(ShopMenu.getMenu());
            }
        });

        battleMenuButton.setOnAction(e -> enterSubMenu(GameModeMenu.getMenu()));
        craftButton.setOnAction(e -> {
            CraftingMenu.getMenu().getGraphic().init();
            CraftingMenu.getHeroMenu().getGraphic().init();
            CraftingMenu.getMenu().getGraphic().init();
            CraftingMenu.getMenu().getGraphic().init();
            enterSubMenu(CraftingMenu.getMenu());
        });
        battleMenuButton.setOnAction(e -> enterSubMenu(ChooseBattleModeMenu.getMenu()));
        saveButton.setOnAction(e -> Primary.saveAccounts());
    }

    private void enterSubMenu(Menu subMenu){
        MenuHandler.enterMenu(menu.enter(subMenu));
    }
}
