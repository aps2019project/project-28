package Controller.menu.Graphics.FXMLController;

import Controller.menu.Battle;
import View.GraphicView;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stuff.Resources;

import java.util.Objects;

public class showCollectableFXMLC extends FXMLController {

    public GridPane gridPane;
    private Stage stage ;
    public static showCollectableFXMLC makeNewScene() {
        try {
            Parent root;
            String rootPath = "Controller/menu/Graphics/FXMLs/ShowCollectable.fxml";
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(DeckSelectorFXMLC.class.getClassLoader().getResource(rootPath)));
            root = rootLoader.load();
            Scene scene = new Scene(root, GraphicView.getPrimaryScreenBounds().getWidth()/1.8, GraphicView.getPrimaryScreenBounds().getHeight()/2);
            scene.setOnMouseEntered(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));
            scene.setOnMouseMoved(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));
            showCollectableFXMLC controller = rootLoader.getController();
            controller.show();
            return controller;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    public void show() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                stage.hide();
            }
        });
        Battle.getMenu().showCollectable();
    }
}
