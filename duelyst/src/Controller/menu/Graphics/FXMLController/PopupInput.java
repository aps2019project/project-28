package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stuff.Resources;

import java.util.Objects;

public class PopupInput {
    private Stage stage ;
    @FXML
    private TextField textField ;
    @FXML
    private Button ok , cancel;


    public static void popup(String text , PopupInputHaving menu ) {
        try {
            Parent root;
            String rootPath = "Controller/menu/Graphics/FXMLs/PopupInput.fxml";
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(PopupInput.class.getClassLoader().getResource(rootPath)));
            root = rootLoader.load();
            Scene scene = new Scene(root, 671, 200);
            scene.setOnMouseEntered(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));
            scene.setOnMouseMoved(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));

            PopupInput controller = rootLoader.getController();
            controller.show(scene , text , menu);
        }catch (Exception e){}
    }

    public void show(Scene scene , String text , PopupInputHaving menu) {
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/Popup.css");
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                stage.hide();
            }
        });

        ok.setOnAction(e -> {
            if (!textField.getText().isEmpty()) {
                stage.close();
                menu.getPopupResult(textField.getText());
            }
        });
        cancel.setOnAction(e -> stage.close());
        GraphicsControls.setButtonStyle("menu-button" , ok , cancel);

        textField.setPromptText(text);

        stage.setScene(scene);
        stage.show() ;
    }

}
