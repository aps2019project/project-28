package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Model.card.Card;
import Model.item.Item;
import Model.item.Usable;
import View.MenuHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopMenuFXMLC extends FXMLController {

    @FXML
    private Button backButton , cardTab , itemTab;
    @FXML
    private Label balance ;
    @FXML
    private ScrollPane scrollPane ;
    private VBox cardsVbox = new VBox() , itemsVbox = new VBox() ;
    private List<Card> cards = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();
        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/ShopMenu.css");
        new Thread(this::makeCardsVBox).start();
        scrollPane.setContent(cardsVbox);
        new Thread(this::makeItemsVBox);

        GraphicsControls.setBackButtonOnPress(backButton);
        backButton.setOnAction(e -> {
//            ShopMenu.getMenu().save() ;
            MenuHandler.exitMenu();
        });

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
        updateBalance();

    }

    public void updateBalance() {
        balance.setText("Balance : " + menu.getAccount().getMoney() + "$");
    }

    private void makeCardsVBox() {
        cardsVbox.setSpacing(15);
        for (Card cart : Card.getCards()) {
            if (!cards.contains(cart)) {
                cards.add(cart);
                FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "Controller/menu/Graphics/FXMLs/CardCard.fxml")));
                try {
                    Parent card = rootLoader.load();
                    CardCardFXMLC fxmlc = rootLoader.getController();
                    fxmlc.buildCardCard(cart, menu.getAccount(), this);
                    cardsVbox.getChildren().add(card);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void makeItemsVBox() {
        itemsVbox.setSpacing(15);

        for (Item item : Usable.getItems()){
            if (item instanceof Usable && (!items.contains(item))){
                items.add(item);
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
//        ShopMenu.getMenu().save() ;
        tab.getStyleClass().add("tab-button-selected");
    }

    private void tabReleased(Button tab) {
        tab.getStyleClass().remove("tab-button-selected");
    }
}
