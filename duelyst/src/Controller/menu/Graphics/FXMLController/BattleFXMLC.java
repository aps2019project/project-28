package Controller.menu.Graphics.FXMLController;

import Controller.menu.Battle;
import Model.account.Hand;
import Model.card.Card;
import Model.card.hermione.Hero;
import exeption.InvalidCardException;
import exeption.InvalidItemException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;


public class BattleFXMLC extends FXMLController {

    @FXML
    public Button endTurn;
    @FXML
    public Button graveYard;
    public ImageView hand0;
    public ImageView hand1;
    public ImageView hand2;
    public ImageView hand3;
    public ImageView hand4;
    @FXML
    private AnchorPane frame;
    @FXML
    private GridPane gridPane;
    private Double mapX;
    private Double mapY;
    private Double cellWidth;
    private Double cellHeight;

    @Override
    public void buildScene() {
        super.buildScene();
//        Hand hand = Battle.getMenu().getPlayer().getHand();
//        hand0 = new ImageView("resources/ui/collection_card_rarity_common.png");
//        hand1 = new ImageView("resources/ui/collection_card_rarity_common.png");
//        hand2 = new ImageView("resources/ui/collection_card_rarity_common.png");
//        hand3 = new ImageView("resources/ui/collection_card_rarity_common.png");
//        hand4 = new ImageView("resources/ui/collection_card_rarity_common.png");
    }

    public void addToScene(Node... nodes){
        this.frame.getChildren().addAll(nodes);
    }

    public Double getMapX() {
        mapX = gridPane.getLayoutX();
        cellWidth = gridPane.getWidth()/9;
        return mapX;
    }

    public Double getMapY() {
        mapY = gridPane.getLayoutY();
        cellHeight = gridPane.getHeight()/5;
        return mapY;
    }

    public Double getCellWidth() {
        return cellWidth;
    }

    public Double getCellHeight() {
        return cellHeight;
    }

    public void removeFromScene(Node... nodes) {
        this.frame.getChildren().removeAll(nodes);
    }

    public double getMapWidth() {
        return gridPane.getWidth();
    }

    public double getMapHeight(){
        return gridPane.getHeight();
    }

    private void setCellEvents() {
        cell00.setStyle("cell");
        cell00.setOnMouseEntered(event -> cell00.getStyleClass().add("cellSelected"));
        cell00.setOnMouseExited(event -> cell00.getStyleClass().remove("cellSelected"));
    }

    public Rectangle cell00;
    public Rectangle cell01;
    public Rectangle cell02;
    public Rectangle cell03;
    public Rectangle cell04;
    public Rectangle cell10;
    public Rectangle cell11;
    public Rectangle cell12;
    public Rectangle cell13;
    public Rectangle cell14;
    public Rectangle cell20;
    public Rectangle cell21;
    public Rectangle cell22;
    public Rectangle cell23;
    public Rectangle cell24;
    public Rectangle cell30;
    public Rectangle cell31;
    public Rectangle cell32;
    public Rectangle cell33;
    public Rectangle cell34;
    public Rectangle cell40;
    public Rectangle cell41;
    public Rectangle cell42;
    public Rectangle cell43;
    public Rectangle cell44;
    public Rectangle cell50;
    public Rectangle cell51;
    public Rectangle cell52;
    public Rectangle cell53;
    public Rectangle cell54;
    public Rectangle cell60;
    public Rectangle cell61;
    public Rectangle cell62;
    public Rectangle cell63;
    public Rectangle cell64;
    public Rectangle cell70;
    public Rectangle cell71;
    public Rectangle cell72;
    public Rectangle cell73;
    public Rectangle cell74;
    public Rectangle cell80;
    public Rectangle cell81;
    public Rectangle cell82;
    public Rectangle cell83;
    public Rectangle cell84;
}
