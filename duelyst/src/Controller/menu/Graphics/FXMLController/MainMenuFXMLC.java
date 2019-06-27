package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
import Model.account.Account;
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
            CollectionMenu.getMenu().getGraphic().init();
            enterSubMenu(CollectionMenu.getMenu());
        });
        shopMenuButton.setOnAction(e -> {
            ShopMenu.getMenu().getGraphic().init();
            enterSubMenu(ShopMenu.getMenu());
        });
        battleMenuButton.setOnAction(e -> enterSubMenu(GameModeMenu.getMenu()));
        craftButton.setOnAction(e -> enterSubMenu(CraftingMenu.getMenu()));
        saveButton.setOnAction(e -> Account.save());
    }

    private void enterSubMenu(Menu subMenu){
        MenuHandler.setCurrentMenu(menu.enter(subMenu));
    }
}
