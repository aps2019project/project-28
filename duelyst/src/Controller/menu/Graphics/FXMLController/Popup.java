package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stuff.Resources;

import java.util.Objects;

public class Popup {
    private Stage stage ;
    @FXML
    private Text textBox ;
    @FXML
    private Button ok ;


    public static void popup(String text) {
        try {
            Parent root;
            String rootPath = "Controller/menu/Graphics/FXMLs/Popup.fxml";
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(Popup.class.getClassLoader().getResource(rootPath)));
            root = rootLoader.load();
            Scene scene = new Scene(root, 671, 200);
            scene.setOnMouseEntered(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));
            scene.setOnMouseMoved(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));

            Popup controller = rootLoader.getController();
            controller.show(scene , text);
        }catch (Exception e){}
    }

    public void show(Scene scene , String text) {
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/Popup.css");
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                stage.hide();
            }
        });

        ok.setOnAction(e -> stage.close());
        GraphicsControls.setButtonStyle("menu-button" , ok);
        textBox.setText(text);
        stage.setScene(scene);
        stage.show() ;
    }

}
