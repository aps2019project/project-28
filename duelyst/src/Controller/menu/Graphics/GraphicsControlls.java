package Controller.menu.Graphics;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class GraphicsControlls {
    public static void setButtonPressedStyles(Button button, String onPressStyle){
        button.setOnMousePressed(e -> button.getStyleClass().add(onPressStyle));
        button.setOnMouseReleased(e-> button.getStyleClass().remove(onPressStyle));
    }
}
