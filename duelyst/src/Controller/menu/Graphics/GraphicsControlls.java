package Controller.menu.Graphics;

import javafx.scene.control.Button;

public class GraphicsControlls {
    public static void setButtonPressedStyles(Button button, String onPressStyle){
        button.setOnMousePressed(e -> button.getStyleClass().add(onPressStyle));
        button.setOnMouseReleased(e-> button.getStyleClass().remove(onPressStyle));
    }
}
