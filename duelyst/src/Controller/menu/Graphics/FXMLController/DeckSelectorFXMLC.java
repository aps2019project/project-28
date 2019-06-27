package Controller.menu.Graphics.FXMLController;

import Controller.menu.DeckSelectorHavingMenu;
import Controller.menu.Menu;
import Model.account.Account;
import Model.account.Deck;
import View.GraphicView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stuff.Resources;

import java.util.Objects;

public class DeckSelectorFXMLC {
//    private static DeckSelectorFXMLC deckSelector;
    private Stage stage ;
    @FXML
    private ScrollPane scrollPane ;
    @FXML
    private Label title ;
    private VBox vbox ;

//
//    public static DeckSelectorFXMLC getDeckSelector(){
//        if (deckSelector == null) deckSelector = new DeckSelectorFXMLC() ;
//        return deckSelector;
//    }
//    public DeckSelectorFXMLC(){
//        deckSelector =this;
//    }

    public static void makeNewScene(Account account , DeckSelectorHavingMenu menu , String title) {
        try {
            Parent root;
            String rootPath = "Controller/menu/Graphics/FXMLs/DeckSelector.fxml";
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(DeckSelectorFXMLC.class.getClassLoader().getResource(rootPath)));
            root = rootLoader.load();
            Scene scene = new Scene(root, GraphicView.getPrimaryScreenBounds().getWidth()/1.8, GraphicView.getPrimaryScreenBounds().getHeight()/2);
            scene.setOnMouseEntered(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));
            scene.setOnMouseMoved(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));
            if (title.isEmpty()) title = account.getName() + "! " + "Select your Legendary Deck" ;

            DeckSelectorFXMLC controller = rootLoader.getController();
            controller.show(account,scene , menu , title);
        }catch (Exception e){e.printStackTrace();}
    }

    public void show(Account account , Scene scene ,DeckSelectorHavingMenu menu , String titleText) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                stage.hide();
            }
        });

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/DeckSelector.css");

        title.setText(titleText);

        scrollPane =(ScrollPane) scene.lookup("#scrollPane");
        vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setFillWidth(true);
        vbox.setMinWidth(400);
        vbox.setSpacing(30);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        int index = 1;
        for (Deck deck : account.getCollection().getDecks()){
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_LEFT);
            Label label = new Label(index+"- " + deck.getName());
            if (deck.getHero() != null) {
                label.setText(label.getText() + " (" + deck.getHero().getName() + ")");
                label.setOnMouseClicked(e -> {
                    account.getCollection().setMainDeck(deck);
                    menu.selectDeck(account, deck);
                    stage.close();
                });
                label.setCursor(new ImageCursor(new Image(Resources.mouse_assist.getPath())));
            }else{
                label.setText(label.getText() + " ( No hero ! )");
                label.setCursor(new ImageCursor(new Image(Resources.mouse_disabled.getPath())));
            }
            label.getStyleClass().add("nameLabel");
            hbox.getChildren().add(label);
            vbox.getChildren().add(hbox);
            index++ ;
        }
        scrollPane.setContent(vbox);
        stage.setScene(scene);
        stage.show() ;
    }

}
