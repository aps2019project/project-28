package Controller.menu.Graphics.FXMLController;

import Controller.menu.CustomModeMenu;
import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.Menu;
import Model.card.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class ShopMenuFXMLC extends FXMLController {

    @FXML
    private Button backButton , cardTab , itemTab;
    @FXML
    private ScrollPane scrollPane ;
    private VBox cardsVbox = new VBox() , itemsVbox = new VBox();

    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();
        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/ShopMenu.css");
        makeCardsVBox();
        scrollPane.setContent(cardsVbox);
        makeItemsVBox();

        GraphicsControls.setBackButtonOnPress(backButton);
        tabPressed(cardTab);
        cardTab.setOnMousePressed(e -> tabPressed(cardTab));
        itemTab.setOnMousePressed(e -> tabPressed(itemTab));
        cardTab.setOnMouseReleased(e -> tabReleased(cardTab));
        itemTab.setOnMouseReleased(e -> tabReleased(itemTab));
        cardTab.setOnAction(e -> {
            tabPressed(cardTab);
            tabReleased(itemTab);
            scrollPane.setContent(cardsVbox);
        });
        itemTab.setOnAction(e -> {
            tabPressed(itemTab);
            tabReleased(cardTab);
            scrollPane.setContent(itemsVbox);
        });
        scrollPane.setPadding(new Insets(0 , 60 , 0 , 60));

    }


    private void makeCardsVBox() {
        cardsVbox.setSpacing(10);

        for (Card cart : Card.getCards()) {
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                    "Controller/menu/Graphics/FXMLs/CardCard.fxml")));
            try {
                Parent card = rootLoader.load();
                 CardCardFXMLC fxmlc = rootLoader.getController();
                 fxmlc.buildCardCard(cart , menu.getAccount());
                cardsVbox.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void makeItemsVBox() {

    }

    private void tabPressed(Button tab) {
        tab.getStyleClass().add("tab-button-selected");
    }

    private void tabReleased(Button tab) {
        tab.getStyleClass().remove("tab-button-selected");
    }

    private void enterSubMenu(Menu subMenu){
        menu.enter(subMenu);
    }
}
