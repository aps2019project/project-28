package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.account.Shop;
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
import javafx.scene.layout.HBox;
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
    @FXML
    private VBox theRoot ;
    private VBox cardsVbox = new VBox() , itemsVbox = new VBox() ;
    private List<Card> cards = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private int selectedTab ;
    private SearchBarFXMLC searchBarFXMLC ;
    private boolean hasSearchBar = false ;

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
            ShopMenu.getMenu().save() ;
            MenuHandler.exitMenu();
        });

        if (!hasSearchBar) {
            hasSearchBar = true ;
            theRoot.getChildren().remove(scrollPane);
            searchBarFXMLC = GraphicsControls.addSearchBar(theRoot, this.getClass());
            theRoot.getChildren().add(scrollPane);
            searchBarFXMLC.getFindButton().setOnAction(e -> search());
        }


        setTabPressedStuff();

    }

    private void search() {
        String search = searchBarFXMLC.getSearchText();
        VBox v ;
        if (search.isEmpty()) {
            if (selectedTab == 1) v = cardsVbox ;
            else v = itemsVbox ;
            scrollPane.setContent(v);
            return ;
        }

        v = new VBox() ;
        v.setSpacing(15);
        for (Card card : Shop.getInstance().getCollection().getCards()){
            if (card.getName().contains(search)){
                makeCardCard(card , v);
            }
        }
        for (Usable item : menu.getAccount().getCollection().getItems()){
            if (item.getName().contains(search)){
                makeItemCard(item , v);
            }
        }
        scrollPane.setContent(v);
    }


    public void updateBalance() {
        balance.setText("Balance : " + menu.getAccount().getMoney() + "$");
    }

    private void makeCardsVBox() {
        cardsVbox.setSpacing(15);
        for (Card cart : Card.getCards()) {
            if (!cards.contains(cart)) {
                cards.add(cart);
                makeCardCard(cart , cardsVbox);
            }
        }
    }

    private void makeCardCard(Card cart , VBox vbox) {
        FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                "Controller/menu/Graphics/FXMLs/CardCard.fxml")));
        try {
            Parent card = rootLoader.load();
            CardCardFXMLC fxmlc = rootLoader.getController();
            fxmlc.buildCardCard(cart, menu.getAccount(), this);
            vbox.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void makeItemsVBox() {
        itemsVbox.setSpacing(15);

        for (Item item : Usable.getItems()){
            if (item instanceof Usable && (!items.contains(item))){
                items.add(item);
                makeItemCard((Usable) item , itemsVbox);
            }
        }

    }

    private void makeItemCard(Usable item, VBox vbox) {
        FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                "Controller/menu/Graphics/FXMLs/ItemCard.fxml")));
        try {
            Parent card = rootLoader.load();
            ItemCardFXMLC fxmlc = rootLoader.getController();
            fxmlc.builditemCard(item, menu.getAccount() , this);
            vbox.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setTabPressedStuff() {
        tabPressed(cardTab);
        cardTab.setOnMousePressed(e -> tabPressed(cardTab));
        itemTab.setOnMousePressed(e -> tabPressed(itemTab));
        cardTab.setOnMouseReleased(e -> tabReleased(cardTab));
        itemTab.setOnMouseReleased(e -> tabReleased(itemTab));
        cardTab.setOnAction(e -> {
            selectedTab = 1 ;
            tabPressed(cardTab);
            tabReleased(itemTab);
            scrollPane.setContent(cardsVbox);
        });
        itemTab.setOnAction(e -> {
            selectedTab = 2;
            tabPressed(itemTab);
            tabReleased(cardTab);
            scrollPane.setContent(itemsVbox);
        });
        scrollPane.setPadding(new Insets(0 , 60 , 0 , 60));
        updateBalance();
    }

    private void tabPressed(Button tab) {
//        ShopMenu.getMenu().save() ;
        tab.getStyleClass().add("tab-button-selected");
    }

    private void tabReleased(Button tab) {
        tab.getStyleClass().remove("tab-button-selected");
    }
}
