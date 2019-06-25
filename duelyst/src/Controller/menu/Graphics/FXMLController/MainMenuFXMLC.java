package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
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




    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/Menu.css");
        exitMenuButton.setStyle("-fx-text-fill: red;");

        GraphicsControls.setButtonStyle("menu-button" , battleMenuButton,
                collectionMenuButton, shopMenuButton, exitMenuButton);


                exitMenuButton.setOnAction(e->{
                    SignInMenu.getMenu().logOut();
                    enterSubMenu(SignInMenu.getMenu());
                });


        collectionMenuButton.setOnAction(e -> enterSubMenu(CollectionMenu.getMenu()));
        shopMenuButton.setOnAction(e -> enterSubMenu(ShopMenu.getMenu()));
        battleMenuButton.setOnAction(e -> enterSubMenu(GameModeMenu.getMenu()));
    }

    private void enterSubMenu(Menu subMenu){
        MenuHandler.setCurrentMenu(menu.enter(subMenu));
    }
}
