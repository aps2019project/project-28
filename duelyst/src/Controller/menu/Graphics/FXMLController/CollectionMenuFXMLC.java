package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
import Model.item.Usable;
import View.MenuHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/CollectionMenu.css");
        buildCardsVbox() ;
        scrollPane.setContent(hermionesVbox);
        buildItemsVbox();

        GraphicsControls.setBackButtonOnPress(backButton);
        backButton.setOnAction(e -> {
//            ShopMenu.getMenu().save() ;
            MenuHandler.goBack();
        });
        setTabPressedStuff();
        updateBalance();
    }

    private void buildCardsVbox() {
        List<HBox> hermionehBoxes = new ArrayList<>();
        List<HBox> spellHboxes = new ArrayList<>();
        for (Card card : menu.getAccount().getCollection().getCards()){
            if (card instanceof Hermione){
                Hermione h = (Hermione)card ;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "Controller/menu/Graphics/FXMLs/CollectionCardHermione.fxml")));
                try {
                    Parent root = loader.load();
                    CollectionCardHermioneFXMLC fxmlc = loader.getController();
                    fxmlc.buildCardCard(h);
                    setUpTheHbox(root , hermionesVbox , hermionehBoxes);
                }catch (IOException ignored) {
                    System.err.println("could'nt load the collectionHermioneCard");
                }
            }
            else if (card instanceof Spell){
                Spell s = (Spell) card ;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "Controller/menu/Graphics/FXMLs/CollectionCardSpell.fxml")));
                try {
                    Parent root = loader.load();
                    CollectionCardSpellFXMLC fxmlc = loader.getController();
                    fxmlc.buildCardCard(s , false);
                    setUpTheHbox(root , spellsVbox , spellHboxes);
                }catch (IOException ignored) {
                    System.err.println("could'nt load the collectionSpellCard");
                }
            }
        }
    }


    private void buildItemsVbox() {
        List<HBox> hBoxes = new ArrayList<>();
        for (Usable usabel : menu.getAccount().getCollection().getUsables()){
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                    "Controller/menu/Graphics/FXMLs/CollectionCardItem.fxml")));
            try {
                Parent root = loader.load();
                CollectionCardItemFXMLC fxmlc = loader.getController();
                fxmlc.buildCardCard(usabel , false);
                setUpTheHbox(root , itemsVbox , hBoxes);
            }catch (IOException ignored) {
                System.err.println("could'nt load the collectionSpellCard");
            }
        }
    }

    private void setUpTheHbox(Parent card, VBox vbox , List<HBox> hBoxes) {
        vbox.setSpacing(15);
        HBox hbox ;
        if (hBoxes.size() == 0 || hBoxes.get(hBoxes.size() - 1).getChildren().size() == 5){
            hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(15);
            hbox.setFillHeight(true);
            hbox.setPrefWidth(vbox.getWidth());
            hBoxes.add(hbox);
            vbox.getChildren().add(hbox);
        }
        hbox = hBoxes.get(hBoxes.size()-1);
        hbox.getChildren().add(card);

    }

    private void setTabPressedStuff() {
        tabPressed(hermioneTab);
        hermioneTab.setOnMousePressed(e -> tabPressed(hermioneTab));
        itemTab.setOnMousePressed(e -> tabPressed(itemTab));
        spellTab.setOnMousePressed(e -> tabPressed(spellTab));
        hermioneTab.setOnMouseReleased(e -> tabReleased(hermioneTab));
        itemTab.setOnMouseReleased(e -> tabReleased(itemTab));
        spellTab.setOnMouseReleased(e -> tabReleased(spellTab));
        hermioneTab.setOnAction(e -> {
            tabPressed(hermioneTab);
            tabReleased(itemTab);
            tabReleased(spellTab);
            scrollPane.setContent(hermionesVbox);
        });
        itemTab.setOnAction(e -> {
            tabPressed(itemTab);
            tabReleased(hermioneTab);
            tabReleased(spellTab);
            scrollPane.setContent(itemsVbox);
        });
        spellTab.setOnAction(e -> {
            tabPressed(spellTab);
            tabReleased(hermioneTab);
            tabReleased(itemTab);
            scrollPane.setContent(spellsVbox);
        });
    }


    private void tabPressed(Button tab) {
//        ShopMenu.getMenu().save() ;
        tab.getStyleClass().add("tab-button-selected");
        updateBalance();
    }

    private void tabReleased(Button tab) {
        tab.getStyleClass().remove("tab-button-selected");
    }

    public void updateBalance() {
        balance.setTextFill(Color.WHITE);
        balance.setText("Balance : " + menu.getAccount().getMoney() + "$");
    }
}
