package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControlls;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/MainMenu.css");
        exitMenuButton.setStyle("-fx-text-fill: red;");

        GraphicsControlls.setButtonStyle("menu-button" , battleMenuButton,
                collectionMenuButton, shopMenuButton, exitMenuButton);


                exitMenuButton.setOnAction(e->{
                    SignInMenu.getMenu().logOut();
                    enterSubMenu(SignInMenu.getMenu());
                });


        collectionMenuButton.setOnAction(e -> enterSubMenu(CollectionMenu.getMenu()));
        shopMenuButton.setOnAction(e -> enterSubMenu(ShopMenu.getMenu()));
        battleMenuButton.setOnAction(e -> enterSubMenu(ChooseBattleModeMenu.getMenu()));
    }

    private void enterSubMenu(Menu subMenu){
        menu.enter(subMenu);
    }
}
