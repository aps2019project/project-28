package Controller.menu.Graphics.FXMLController;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class BattleFXMLC extends FXMLController {

    @FXML
    public Button endTurn;
    @FXML
    public Button graveYard;
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
        Scene scene = menu.getGraphic().getScene();


    }

    public void addToScene(Node... nodes){
        this.frame.getChildren().addAll(nodes);
    }

    public Double getMapX() {
        mapX = gridPane.getLayoutX();
        cellWidth = mapX / 9;
        return mapX;
    }

    public Double getMapY() {
        mapY = gridPane.getLayoutY();
        cellHeight = mapY/5;
        return mapY;
    }

    public Double getCellWidth() {
        return cellWidth;
    }

    public Double getCellHeight() {
        return cellHeight;
    }
}
