package Controller.menu.Graphics.FXMLController;

import Controller.menu.CollectionMenu;
import Controller.menu.DeckSelectorHavingMenu;
import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.Primary;
import Model.account.Account;
import Model.account.Deck;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
import Model.item.Item;
import Model.item.Usable;
import View.Listeners.OnDeckSelector2ClickedListener;
import View.Listeners.OnDeckSelectorClickedListener;
import View.MenuHandler;
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

public class CollectionMenuFXMLC extends FXMLController implements PopupInputHaving , SearchBarHaving, DeckSelectorHavingMenu2 {

    private OnDeckSelector2ClickedListener onDeckSelector2ClickedListener ;

    @FXML
    private Button backButton , hermioneTab, itemTab , spellTab , decksTab;
    @FXML
    private Label balance ;
    @FXML
    private ScrollPane scrollPane ;
    @FXML
    private VBox theRoot ;
    private VBox hermionesVbox = new VBox() , spellsVbox = new VBox() , itemsVbox = new VBox() ,
            decksVbox = new VBox() , decksVbox2 = new VBox();
    private int selectedTab = 1 ;
    private SearchBarFXMLC searchBarFXMLC ;
    private boolean hasSearchBar = false ;


    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();
        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/CollectionMenu.css");
        buildCardsVbox();
        scrollPane.setContent(hermionesVbox);
        new Thread(() -> {
            buildItemsVbox();
            buildDecksVbox();
        }).start();

        GraphicsControls.setBackButtonOnPress(backButton);

        setTabPressedStuff();
        updateBalance();


        if (!hasSearchBar) {
            hasSearchBar = true ;
            theRoot.getChildren().remove(scrollPane);
            searchBarFXMLC = GraphicsControls.addSearchBar(theRoot, this);
            theRoot.getChildren().add(scrollPane);
            searchBarFXMLC.getFindButton().setOnAction(e -> search());
        }
    }

    @Override
    public void search() {
        String search = searchBarFXMLC.getSearchText();
        VBox v ;
        if (search.isEmpty()) {
            if (selectedTab == 1) v = hermionesVbox ;
            else if (selectedTab == 2) v = spellsVbox ;
            else if (selectedTab == 3) v = itemsVbox ;
            else v = decksVbox ;
            scrollPane.setContent(v);
            return ;
        }

        v = new VBox() ;
        v.setSpacing(15);

        List<HBox> hermionehBoxes = new ArrayList<>();
        List<HBox> spellHboxes = new ArrayList<>();
        for (Card card : menu.getAccount().getCollection().getCards()){
            if (card.getName().toLowerCase().contains(search.toLowerCase())){
                makeCardCard(hermionehBoxes , spellHboxes , card ,v ,v);
            }
        }
        List<HBox> itemHBoxes = new ArrayList<>();
        for (Usable item : menu.getAccount().getCollection().getItems()){
            if (item.getName().contains(search)){
                makeItemCard(itemHBoxes , item , v);
            }
        }
        scrollPane.setContent(v);
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
            selectedTab = 1 ;
        });
        itemTab.setOnAction(e -> {
            tabPressed(itemTab);
            tabReleased(hermioneTab);
            tabReleased(spellTab);
            tabReleased(decksTab);
            scrollPane.setContent(itemsVbox);
            selectedTab = 3 ;
        });
        spellTab.setOnAction(e -> {
            tabPressed(spellTab);
            tabReleased(hermioneTab);
            tabReleased(itemTab);
            tabReleased(decksTab);
            scrollPane.setContent(spellsVbox);
            selectedTab = 2 ;
        });
        decksTab.setOnAction(e -> {
            tabPressed(decksTab);
            tabReleased(hermioneTab);
            tabReleased(itemTab);
            tabReleased(spellTab);
            scrollPane.setContent(decksVbox);
            selectedTab = 4 ;
        });
    }

    private void buildCardsVbox() {
        hermionesVbox.getChildren().clear() ;
        spellsVbox.getChildren().clear() ;
        List<HBox> hermionehBoxes = new ArrayList<>();
        List<HBox> spellHboxes = new ArrayList<>();
        for (Card card : menu.getAccount().getCollection().getCards()){
            makeCardCard(hermionehBoxes, spellHboxes, card , hermionesVbox , spellsVbox);
        }
    }

    private void makeCardCard(List<HBox> hermionehBoxes, List<HBox> spellHboxes, Card card , VBox vboxHermione , VBox vboxSpell) {
        if (card instanceof Hermione){
            Hermione h = (Hermione)card ;
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                    "Controller/menu/Graphics/FXMLs/CollectionCardHermione.fxml")));
            try {
                Parent root = loader.load();
                CollectionCardHermioneFXMLC fxmlc = loader.getController();
                fxmlc.buildCardCard(h);
                setUpTheHbox(root , vboxHermione  , hermionehBoxes);
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
                setUpTheHbox(root , vboxSpell , spellHboxes);
            }catch (IOException ignored) {
                System.err.println("could'nt load the collectionSpellCard");
            }
        }
    }

    private void buildItemsVbox() {
        List<HBox> hBoxes = new ArrayList<>();
        for (Usable usabel : menu.getAccount().getCollection().getUsables()){
            makeItemCard(hBoxes, usabel , itemsVbox);
        }
    }

    private void makeItemCard(List<HBox> hBoxes, Usable usabel , VBox vbox) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                "Controller/menu/Graphics/FXMLs/CollectionCardItem.fxml")));
        try {
            Parent root = loader.load();
            CollectionCardItemFXMLC fxmlc = loader.getController();
            fxmlc.buildCardCard(usabel);
            setUpTheHbox(root , vbox , hBoxes);
        }catch (IOException ignored) {
            System.err.println("could'nt load the collectionSpellCard");
        }
    }

    public void buildDecksVbox() {
        decksVbox.getChildren().clear();
        decksVbox.setSpacing(15);
        decksVbox.getStylesheets().add("Controller/menu/Graphics/StyleSheets/Buttons.css") ;
        //first row
        {
            HBox firstRow = new HBox();
            firstRow.setMinHeight(70);
            firstRow.setPrefHeight(70);
            firstRow.setFillHeight(true);
            firstRow.setStyle("-fx-background-color:#50505050 ; ");
            firstRow.setAlignment(Pos.CENTER);
            Button newDeck = new Button("New Deck");
            newDeck.setFont(new Font("verdana", 13));
            newDeck.setPrefWidth(160);
            newDeck.setPrefHeight(70);
            Button deleteDeck = new Button("Delete Deck");
            deleteDeck.setFont(new Font("verdana", 13));
            deleteDeck.setPrefWidth(160);
            deleteDeck.setPrefHeight(70);
            GraphicsControls.setButtonStyle("menu-button", newDeck, deleteDeck);
            newDeck.setOnAction(e -> newDeck());
            deleteDeck.setOnAction(e -> deleteDeck());
            firstRow.getChildren().addAll(newDeck, deleteDeck);
            decksVbox.getChildren().add(firstRow);
        }
        //second row
        {
            HBox secondRow = new HBox();
            secondRow.setMinHeight(70);
            secondRow.setPrefHeight(70);
            secondRow.setFillHeight(true);
            secondRow.setStyle("-fx-background-color:#50505050 ; ");
            secondRow.setAlignment(Pos.CENTER);
            Button imp = new Button("Import deck") ;
            imp.setFont(new Font("verdana" , 13));
            imp.setPrefWidth(160);
            imp.setPrefHeight(70);
            imp.setOnAction(e -> showDeckSelector2(Primary.getDefaultDecks()));
            GraphicsControls.setButtonStyle("menu-button", imp);
            secondRow.getChildren().add(imp);
            decksVbox.getChildren().add(secondRow);
        }
        for (Deck deck : ((CollectionMenu)menu).getDecks()){
            HBox row = new HBox();
            row.setMinHeight(70);
            row.setSpacing(50);
            row.setPrefHeight(70);
            row.setFillHeight(true);
            row.setStyle("-fx-background-color:#50505050 ; ");
            row.setAlignment(Pos.CENTER);
            Button button = new Button(deck.getName());
            button.setFont(new Font("verdana" , 13));
            button.setPrefWidth(160);
            button.setPrefHeight(70);
            Button exp = new Button("Export");
            exp.setFont(new Font("verdana" , 13));
            exp.setPrefWidth(160);
            exp.setPrefHeight(70);
            GraphicsControls.setButtonStyle("deck-button" , button);
            GraphicsControls.setButtonStyle("battleMenuButton" , exp);
            button.setOnAction(e -> {
                ((CollectionMenu)menu).setSelectedDeck(deck) ;
                buildDecksVbox2();
                scrollPane.setContent(decksVbox2);
            });
            exp.setOnAction(e -> {
                try {
                    menu.getAccount().getCollection().exportDeck(deck.getName());
                } catch (InvalidDeckException | IOException ex) {
                    ex.printStackTrace();
                }
            });

            row.getChildren().addAll(button , exp);
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
        try {
            hbox.getChildren().add(card);
        }catch (Exception e ){
            e.printStackTrace();
        }

    }

    private void setTheButton(Usable item, Button button) {
        if (!((CollectionMenu)menu).isTheItemInTheDeck(item)){
            button.setText("Add");
            button.setOnAction(e -> {
                try {
                    ((CollectionMenu)menu).addToDeck(item.getID(),((CollectionMenu)menu).getSelectedDeck().getName());
                    setTheButton(item , button);
                } catch (FullDeckException ex) {
                    Popup.popup("The selected Deck is full!");
                } catch (DeckAlreadyHasThisItemException ex) {
                    Popup.popup("This deck already has this Item !");
                    ex.printStackTrace();
                } catch (InvalidItemException ex) {
                    ex.printStackTrace();
                } catch (DeckAlreadyHasAHeroException ex) {
                    ex.printStackTrace();
                } catch (InvalidCardException ex) {
                    ex.printStackTrace();
                } catch (DeckAlreadyHasThisCardException ex) {
                    ex.printStackTrace();
                } catch (InvalidDeckException ex) {
                    ex.printStackTrace();
                }
            });
        }else{
            button.setText("Remove");
            button.setOnAction(e->{
                try {
                    ((CollectionMenu)menu).removeFromDeck(item.getID(), ((CollectionMenu)menu).getSelectedDeck().getName());
                    setTheButton(item , button);
                } catch (InvalidItemException ex) {
                    Popup.popup("This item does not exist on this deck !");
                } catch (InvalidCardException ignored) {} catch (InvalidDeckException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    private void setTheButton(Button button , Card card) {
        if (!((CollectionMenu) menu).isTheCardInTheDeck(card)) {
            button.setText("Add");
            button.setOnAction(e -> {
                try {
                    ((CollectionMenu) menu).addToDeck(card.getID(),  ((CollectionMenu)menu).getSelectedDeck().getName());
                    setTheButton(button , card);
                } catch (FullDeckException ex) {
                    Popup.popup("The selected Deck is full!");
                } catch (DeckAlreadyHasAHeroException ex) {
                    Popup.popup("This deck already has a Hero!");
                } catch (DeckAlreadyHasThisCardException ex) {
                    Popup.popup("This deck already has this card !");
                } catch (InvalidItemException | InvalidCardException | DeckAlreadyHasThisItemException | InvalidDeckException ex) {
                    ex.printStackTrace();
                }
            });
        } else {
            button.setText("Remove");
            button.setOnAction(e -> {
                try {
                    ((CollectionMenu) menu).removeFromDeck(card.getID(), ((CollectionMenu)menu).getSelectedDeck().getName());
                    setTheButton(button , card);
                } catch (InvalidCardException ex) {
                    Popup.popup("This card does not exist on this deck !");
                } catch (InvalidItemException ignored) {
                } catch (InvalidDeckException ex) {
                    ex.printStackTrace();
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
//        CollectionMenu.getMenu().save() ;
        tab.getStyleClass().add("tab-button-selected");
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
//            Primary.saveAccounts();
            ((CollectionMenu)menu).setSelectedDeck(deck);
            buildDecksVbox();
            buildDecksVbox2();
            scrollPane.setContent(decksVbox2);
        } catch (DeckAlreadyExistException e) {
            PopupInput.popup("this deck name already exists !" , this);
        }
    }

    @Override
    public void selectDeck2(Deck deck) {
        try {
            menu.getAccount().getCollection().importDeck(deck.getName());
        } catch (InvalidDeckException e) {
            Popup.popup("Invalid Deck for some reason");
        }
    }

    @Override
    public void setDeckSelector2Listener(OnDeckSelector2ClickedListener ds) {
        onDeckSelector2ClickedListener = ds ;
    }

    @Override
    public void showDeckSelector2(List<Deck> decks) {
        onDeckSelector2ClickedListener.show(decks , this , "Which one do you want") ;
    }
}
