package Controller.menu.Graphics.FXMLController;

import Controller.menu.DeckSelectorHavingMenu;
import Model.account.Account;
import Model.account.Deck;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraveYardFXMLC {
    private Stage stage ;
    @FXML
    private ScrollPane scrollPane ;
    @FXML
    private Label title ;
    private VBox vbox ;

    public static void makeNewScene(Account account ) {
        try {
            Parent root;
            String rootPath = "Controller/menu/Graphics/FXMLs/GraveYard.fxml";
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(GraveYardFXMLC.class.getClassLoader().getResource(rootPath)));
            root = rootLoader.load();
            Scene scene = new Scene(root, GraphicView.getPrimaryScreenBounds().getWidth()/1.8, GraphicView.getPrimaryScreenBounds().getHeight()/2);
            scene.setOnMouseEntered(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));
            scene.setOnMouseMoved(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));
            GraveYardFXMLC controller = rootLoader.getController();
            controller.show(account,scene);
        }catch (Exception e){e.printStackTrace();}
    }

    public void show(Account account , Scene scene) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                stage.hide();
            }
        });

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/GraveYard.css");


        scrollPane =(ScrollPane) scene.lookup("#scrollPane");
        vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setFillWidth(true);
        vbox.setMinWidth(400);
        vbox.setSpacing(30);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        List<HBox> hBoxes = new ArrayList<>();
        for (Card card : account.getPlayer().getDeck().getGraveYard()){
            if (card instanceof Hermione){
                Hermione h = (Hermione) card;
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "Controller/menu/Graphics/FXMLs/CollectionCardHermione.fxml")));
                try {
                    Parent root = loader.load();
                    CollectionCardHermioneFXMLC fxmlc = loader.getController();
                    fxmlc.buildCardCard(h);
                    setUpTheHbox(root , vbox , hBoxes);
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
                    setUpTheHbox(root , vbox , hBoxes);
                }catch (IOException ignored) {
                    System.err.println("could'nt load the collectionSpellCard");
                }
            }
        }
        scrollPane.setContent(vbox);
        stage.setScene(scene);
        stage.show() ;
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

}
