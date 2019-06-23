package Controller.menu.Graphics;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class GraphicsControls {
    //will set the buttons styleID and handles the change for when it's pressed !
    public static void setButtonStyle(String buttonStyle , Button...buttons){
        String onPressStyle = buttonStyle + "Clicked" ;
        for (Button button : buttons) {
            if (!button.getStyleClass().contains(buttonStyle)) button.getStyleClass().add(buttonStyle);

            button.setOnMousePressed(e -> button.getStyleClass().add(onPressStyle));
            button.setOnMouseReleased(e -> button.getStyleClass().remove(onPressStyle));
        }
    }
}
