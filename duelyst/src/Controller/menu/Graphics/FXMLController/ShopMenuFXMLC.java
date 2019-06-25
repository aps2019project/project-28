package Controller.menu.Graphics.FXMLController;

import Controller.menu.CustomModeMenu;
import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.Menu;
import Model.card.Card;
import Model.item.Item;
import Model.item.Usable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class ShopMenuFXMLC extends FXMLController {

    @FXML
    private Button backButton , cardTab , itemTab , sellTab;
    @FXML
    private Label balance ;
    @FXML
    private ScrollPane scrollPane ;
    private VBox cardsVbox = new VBox() , itemsVbox = new VBox() , sellTabVbox = new VBox();

    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();
        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/ShopMenu.css");
        makeCardsVBox();
        scrollPane.setContent(cardsVbox);
        makeItemsVBox();
        makeSellTabVbox();

        GraphicsControls.setBackButtonOnPress(backButton);
        tabPressed(cardTab);
        cardTab.setOnMousePressed(e -> tabPressed(cardTab));
        itemTab.setOnMousePressed(e -> tabPressed(itemTab));
        sellTab.setOnMousePressed(e -> tabPressed(sellTab));
        cardTab.setOnMouseReleased(e -> tabReleased(cardTab));
        itemTab.setOnMouseReleased(e -> tabReleased(itemTab));
        sellTab.setOnMouseReleased(e -> tabReleased(sellTab));
        cardTab.setOnAction(e -> {
            tabPressed(cardTab);
            tabReleased(itemTab);
            tabReleased(sellTab);
            scrollPane.setContent(cardsVbox);
        });
        itemTab.setOnAction(e -> {
            tabPressed(itemTab);
            tabReleased(cardTab);
            tabReleased(sellTab);
            scrollPane.setContent(itemsVbox);
        });
        sellTab.setOnAction(e -> {
            tabPressed(itemTab);
            tabReleased(cardTab);
            tabReleased(itemTab);
            scrollPane.setContent(sellTabVbox);
        });
        scrollPane.setPadding(new Insets(0 , 60 , 0 , 60));
        updateBalance();

    }

    public void updateBalance() {
        balance.setText("Balance : " + menu.getAccount().getMoney() + "$");
    }

    private void makeSellTabVbox() {
    }


    private void makeCardsVBox() {
        cardsVbox.setSpacing(15);

        for (Card cart : Card.getCards()) {
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                    "Controller/menu/Graphics/FXMLs/CardCard.fxml")));
            try {
                Parent card = rootLoader.load();
                CardCardFXMLC fxmlc = rootLoader.getController();
                fxmlc.buildCardCard(cart , menu.getAccount() , this);
                cardsVbox.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void makeItemsVBox() {
        itemsVbox.setSpacing(15);

        for (Item item : Usable.getItems()){
            if (item instanceof Usable){
                FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "Controller/menu/Graphics/FXMLs/ItemCard.fxml")));
                try {
                    Parent card = rootLoader.load();
                    ItemCardFXMLC fxmlc = rootLoader.getController();
                    fxmlc.builditemCard((Usable)item , menu.getAccount() , this);
                    itemsVbox.getChildren().add(card);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void tabPressed(Button tab) {
        tab.getStyleClass().add("tab-button-selected");
    }

    private void tabReleased(Button tab) {
        tab.getStyleClass().remove("tab-button-selected");
    }
}
