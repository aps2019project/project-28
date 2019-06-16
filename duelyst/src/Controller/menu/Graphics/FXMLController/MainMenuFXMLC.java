package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControlls;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainMenuFXMLC extends FXMLController {

/*
    private Button battleButton ;
    private Button collectionButton ;
    private Button shopButton ;
    private Button exitButton ;



    public MainMenuFXMLC(Menu menu) {
        super(menu);
    }
    public MainMenuFXMLC() {
        super();
    }


    @Override
    public void buildScene() {
        super.buildScene();
        scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Scenes/StyleSheets/MainMenu.css");
        battleButton = (Button)scene.lookup("#battleMenuButton");
        collectionButton = (Button)scene.lookup("#collectionMenuButton");
        shopButton = (Button)scene.lookup("#shopMenuButton");
        exitButton = (Button)scene.lookup("#exitMenuButton");
        exitButton.setStyle("-fx-text-fill: red;");

        GraphicsControlls.setButtonStyle("menu-button" , battleButton ,
                collectionButton , shopButton , exitButton );

        exitButton.setOnAction(e -> ((SignInMenu)menu.getParentMenu()).logOut());
        collectionButton.setOnAction(e -> enterSubMenu(CollectionMenu.getMenu()));
        shopButton.setOnAction(e -> enterSubMenu(ShopMenu.getMenu()));
        battleButton.setOnAction(e -> enterSubMenu(ChooseBattleModeMenu.getMenu()));
    }

    private void enterSubMenu(Menu subMenu){
        menu.enter(subMenu);
    }*/
}
