package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.Menu;
import Model.account.Account;
import Model.account.Deck;
import Model.card.Card;
import Model.item.Item;
import Model.item.Usable;
import View.GraphicView;
import exeption.DeckAlreadyExistException;
import exeption.InvalidDeckException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stuff.Resources;

import java.util.ArrayList;
import java.util.Objects;

public class NewDeckFXMLC {
    private Stage stage ;
    @FXML
    private Button submit ;
    @FXML
    private ScrollPane pane ;
    @FXML
    private TextField name ;

    private VBox vbox = new VBox();


    public static void makeNewScene(Account account){
        try {
            Parent root;
            String rootPath = "Controller/menu/Graphics/FXMLs/NewDeck.fxml";
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(DeckSelectorFXMLC.class.getClassLoader().getResource(rootPath)));
            root = rootLoader.load();
            Scene scene = new Scene(root, GraphicView.getPrimaryScreenBounds().getWidth()/1.8, GraphicView.getPrimaryScreenBounds().getHeight()/2);
            scene.setOnMouseEntered(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));
            scene.setOnMouseMoved(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));

            NewDeckFXMLC controller = rootLoader.getController();
            controller.show(account,scene);
        }catch (Exception e){e.printStackTrace();}
    }

    public void show(Account account , Scene scene ){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                stage.hide();
            }
        });
        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/DeckSelector.css");
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setFillWidth(true);
        vbox.setMinWidth(400);
        vbox.setSpacing(30);
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);

        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Usable> usables = new ArrayList<>();


        GraphicsControls.setButtonStyle("shopping-button" , submit);
        for (Card card : account.getCollection().getCards()){
            CheckBox checkBox = new CheckBox();
            checkBox.setText(card.getName());
            vbox.getChildren().add(checkBox);
            checkBoxes.add(checkBox);
            cards.add(card);
            checkBox.setOnAction(e -> {
                if (cards.contains(card)) usables.remove(card);
                else cards.add(card);
            });
        }
        for (Usable item : account.getCollection().getItems()){
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getName());
            vbox.getChildren().add(checkBox);
            checkBoxes.add(checkBox);
            usables.add(item);
            checkBox.setOnAction(e -> {
                if (usables.contains(item)) usables.remove(item);
                else usables.add(item);
            });
        }

        submit.setOnAction(e -> {
            if (!name.getText().isEmpty()){
                try {
                    account.getCollection().addNewDeck(name.getText());
                    try {
                        Deck deck = account.getCollection().getDeckByName(name.getText());
                        for (Card card : cards) {
                            deck.addCardToDeck(card);
                        }
                        for (Usable usable : usables){
                            deck.addItemToDeck(usable);
                        }
                    }catch (Exception ex){
                        System.err.println("couldn't do it !");
                        ex.printStackTrace();
                    }
                }catch (DeckAlreadyExistException ignored){
                    name.setText("");
                    name.setPromptText("choose a new name!");
                }
            }
        });


        pane.setContent(vbox);
        stage.setScene(scene);
        stage.show() ;
    }
}
