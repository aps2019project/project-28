package Controller.menu.Graphics.FXMLController;

import Controller.menu.CollectionMenu;
import Controller.menu.Graphics.GraphicsControls;
import Model.account.Deck;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
import Model.item.Usable;
import exeption.*;
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
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectionMenuFXMLC extends FXMLController implements PopupInputHaving {

    @FXML
    private Button backButton , hermioneTab, itemTab , spellTab , decksTab;
    @FXML
    private Label balance ;
    @FXML
    private ScrollPane scrollPane ;
    private VBox hermionesVbox = new VBox() , spellsVbox = new VBox() , itemsVbox = new VBox() ,
            decksVbox = new VBox() , decksVbox2 = new VBox();


    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();
        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/CollectionMenu.css");
        buildCardsVbox() ;
        scrollPane.setContent(hermionesVbox);
        buildItemsVbox();
        buildDecksVbox();

        GraphicsControls.setBackButtonOnPress(backButton);
        backButton.setOnAction(e -> {
//            ShopMenu.getMenu().save() ;
//            MenuHandler.exitMenu();

        });
        setTabPressedStuff();
        updateBalance();
    }

    private void setTabPressedStuff() {
        tabPressed(hermioneTab);
        hermioneTab.setOnMousePressed(e -> tabPressed(hermioneTab));
        itemTab.setOnMousePressed(e -> tabPressed(itemTab));
        spellTab.setOnMousePressed(e -> tabPressed(spellTab));
        decksTab.setOnMousePressed(e -> tabPressed(decksTab));
        hermioneTab.setOnMouseReleased(e -> tabReleased(hermioneTab));
        itemTab.setOnMouseReleased(e -> tabReleased(itemTab));
        spellTab.setOnMouseReleased(e -> tabReleased(spellTab));
        decksTab.setOnMouseReleased(e -> tabReleased(decksTab));
        hermioneTab.setOnAction(e -> {
            tabPressed(hermioneTab);
            tabReleased(itemTab);
            tabReleased(spellTab);
            tabReleased(decksTab);
            scrollPane.setContent(hermionesVbox);
        });
        itemTab.setOnAction(e -> {
            tabPressed(itemTab);
            tabReleased(hermioneTab);
            tabReleased(spellTab);
            tabReleased(decksTab);
            scrollPane.setContent(itemsVbox);
        });
        spellTab.setOnAction(e -> {
            tabPressed(spellTab);
            tabReleased(hermioneTab);
            tabReleased(itemTab);
            tabReleased(decksTab);
            scrollPane.setContent(spellsVbox);
        });
        decksTab.setOnAction(e -> {
            tabPressed(decksTab);
            tabReleased(hermioneTab);
            tabReleased(itemTab);
            tabReleased(spellTab);
            scrollPane.setContent(decksVbox);
        });
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
                    fxmlc.buildCardCard(s);
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
                fxmlc.buildCardCard(usabel);
                setUpTheHbox(root , itemsVbox , hBoxes);
            }catch (IOException ignored) {
                System.err.println("could'nt load the collectionSpellCard");
            }
        }
    }

    private void buildDecksVbox() {
        decksVbox.getChildren().clear();
        decksVbox.setSpacing(15);
        decksVbox.getStylesheets().add("Controller/menu/Graphics/StyleSheets/Buttons.css") ;

        HBox firstRow = new HBox();
        firstRow.setPrefHeight(70);
        firstRow.setFillHeight(true);
        firstRow.setStyle("-fx-background-color:#50505050 ; ");
        firstRow.setAlignment(Pos.CENTER);
        Button newDeck = new Button("New Deck");
        newDeck.setFont(new Font("verdana" , 13));
        newDeck.setPrefWidth(160);
        newDeck.setPrefHeight(70);
        Button deleteDeck = new Button("Delete Deck");
        newDeck.setFont(new Font("verdana" , 13));
        newDeck.setPrefWidth(160);
        newDeck.setPrefHeight(70);
        GraphicsControls.setButtonStyle("menu-button" , newDeck , deleteDeck);
        newDeck.setOnAction(e -> newDeck());
        deleteDeck.setOnAction(e -> deleteDeck());
        firstRow.getChildren().addAll(newDeck , deleteDeck);
        decksVbox.getChildren().add(firstRow);
        for (Deck deck : menu.getAccount().getCollection().getDecks()){
            HBox row = new HBox();
            row.setPrefHeight(70);
            row.setFillHeight(true);
            row.setStyle("-fx-background-color:#50505050 ; ");
            row.setAlignment(Pos.CENTER);
            Button button = new Button(deck.getName());
            button.setFont(new Font("verdana" , 13));
            button.setPrefWidth(160);
            button.setPrefHeight(70);
            GraphicsControls.setButtonStyle("deck-button" , button);
            button.setOnAction(e -> {
                ((CollectionMenu)menu).setSelectedDeck(deck) ;
                buildDecksVbox2();
                scrollPane.setContent(decksVbox2);
            });
            row.getChildren().add(button);
            decksVbox.getChildren().add(row);
        }

    }

    private void buildDecksVbox2() {
        decksVbox2.getChildren().clear();
        decksVbox2.setSpacing(15);
        ArrayList<HBox> hermionehBoxes = new ArrayList<>();
        ArrayList<HBox> spellHboxes = new ArrayList<>();
        ArrayList<HBox> usableHboxes = new ArrayList<>();
        for (Card card : menu.getAccount().getCollection().getCards()){
            if (card instanceof Hermione){
                Hermione h = (Hermione)card ;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "Controller/menu/Graphics/FXMLs/CollectionCardHermione.fxml")));
                try {
                    Parent root = loader.load();
                    CollectionCardHermioneFXMLC fxmlc = loader.getController();
                    fxmlc.buildCardCard(h);
                    Button button = fxmlc.getButton();
                    setTheButton(button , card);
                    setUpTheHbox(root , decksVbox2 , hermionehBoxes);
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
                    fxmlc.buildCardCard(s);
                    Button button = fxmlc.getButton();
                    setTheButton(button , card);
                    setUpTheHbox(root , decksVbox2 , spellHboxes);
                }catch (IOException ignored) {
                    System.err.println("could'nt load the collectionSpellCard");
                }
            }
        }
        for (Usable item : menu.getAccount().getCollection().getUsables() ) {
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                    "Controller/menu/Graphics/FXMLs/CollectionCardItem.fxml")));
            try {
                Parent card = rootLoader.load();
                CollectionCardItemFXMLC fxmlc = rootLoader.getController();
                fxmlc.buildCardCard(item);
                decksVbox2.getChildren().add(card);
                Button button = fxmlc.getButton();
                setTheButton(item, button);
                setUpTheHbox(card , decksVbox2 , usableHboxes);
            } catch (IOException e) {
                e.printStackTrace();
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

    private void setTheButton(Usable item, Button button) {
        if (((CollectionMenu)menu).isTheItemInTheDeck(item)){
            button.setText("Add");
            button.setOnAction(e -> {
                try {
                    ((CollectionMenu)menu).addItemToDeck(item);
                } catch (FullDeckException ex) {
                    Popup.popup("The selected Deck is full!");
                } catch (DeckAlreadyHasThisItemException ex) {
                    Popup.popup("This deck already has this Item !");
                    ex.printStackTrace();
                }
            });
        }else{
            button.setText("Remove");
            button.setOnAction(e->{
                try {
                    ((CollectionMenu)menu).removeFromDeck(item.getID());
                } catch (InvalidItemException ex) {
                    Popup.popup("This item does not exist on this deck !");
                } catch (InvalidCardException ignored) {}
            });
        }
    }

    private void setTheButton(Button button , Card card) {
        if (((CollectionMenu) menu).isTheCardInTheDeck(card)) {
            button.setText("Add");
            button.setOnAction(e -> {
                try {
                    ((CollectionMenu) menu).addCardToDeck(card);
                } catch (FullDeckException ex) {
                    Popup.popup("The selected Deck is full!");
                } catch (DeckAlreadyHasAHeroException ex) {
                    Popup.popup("This deck already has a Hero!");
                } catch (DeckAlreadyHasThisCardException ex) {
                    Popup.popup("This deck already has this card !");
                    ex.printStackTrace();
                }
            });
        } else {
            button.setText("Remove");
            button.setOnAction(e -> {
                try {
                    ((CollectionMenu) menu).removeFromDeck(card.getID());
                } catch (InvalidCardException ex) {
                    Popup.popup("This card does not exist on this deck !");
                } catch (InvalidItemException ignored) {
                }
            });
        }
    }



    private void deleteDeck() {
        ((CollectionMenu)menu).showDeckSelector(menu.getAccount());
    }

    private void newDeck() {
        PopupInput.popup("new Deck's name" , this);
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

    @Override
    public void getPopupResult(String text) {
        try {
            Deck deck =((CollectionMenu)menu).createNewDeck(text);
            //Account.save();
            buildDecksVbox();
            buildDecksVbox2();
            ((CollectionMenu)menu).setSelectedDeck(deck);
            scrollPane.setContent(decksVbox2);
        } catch (DeckAlreadyExistException e) {
            PopupInput.popup("this deck name already exists !" , this);
        }
    }

}
