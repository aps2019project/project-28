package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.card.Card;
import Model.card.hermione.Hermione;
import View.MenuHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class CollectionMenuFXMLC extends FXMLController {

    @FXML
    private Button backButton , hermioneTab, itemTab , spellTab;
    @FXML
    private Label balance ;
    @FXML
    private ScrollPane scrollPane ;
    private VBox hermionesVbox = new VBox() , spellsVbox = new VBox() , itemsVbox = new VBox();

    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();
        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/ShopMenu.css");
        buildHermionesVbox() ;
        scrollPane.setContent(hermionesVbox);
        buildSpellsVbox();
        buildItemsVbox();

        GraphicsControls.setBackButtonOnPress(backButton);
        backButton.setOnAction(e -> {
//            ShopMenu.getMenu().save() ;
            MenuHandler.goBack();
        });
        setTabPressedStuff();




    }

    private void buildHermionesVbox() {
        for (Card card : menu.getAccount().getCollection().getCards()){
            if (card instanceof Hermione){
                Hermione h = (Hermione)card ;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "Controller/menu/Graphics/FXMLs/CollectionCardHermione.fxml")));
                try {
                    Parent root = loader.load();
                    CollectionCardHermioneFXMLC fxmlc = loader.getController();
                    fxmlc.buildCardCard(h);
                    hermionesVbox.getChildren().add(root);
                }catch (IOException ignored) {
                    System.err.println("could'nt load the collectionHermioneCard");
                }
            }
        }
    }

    private void buildSpellsVbox() {

    }

    private void buildItemsVbox() {

    }


    private void setTabPressedStuff() {
        tabPressed(hermioneTab);
        hermioneTab.setOnMousePressed(e -> tabPressed(hermioneTab));
        itemTab.setOnMousePressed(e -> tabPressed(itemTab));
        spellTab.setOnMousePressed(e -> tabPressed(spellTab));
        hermioneTab.setOnMouseReleased(e -> tabReleased(hermioneTab));
        itemTab.setOnMouseReleased(e -> tabReleased(itemTab));
        spellTab.setOnMouseReleased(e -> tabReleased(spellTab));
    }


    private void tabPressed(Button tab) {
//        ShopMenu.getMenu().save() ;
        tab.getStyleClass().add("tab-button-selected");
    }

    private void tabReleased(Button tab) {
        tab.getStyleClass().remove("tab-button-selected");
    }

    public void updateBalance() {
        balance.setText("Balance : " + menu.getAccount().getMoney() + "$");
    }
}
