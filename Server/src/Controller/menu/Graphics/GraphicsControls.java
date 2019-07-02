package Controller.menu.Graphics;

import View.MenuHandler;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class GraphicsControls {
    //will set the buttons styleID and handles the change for when it's pressed !
    // do not use for backButton !
    public static void setButtonStyle(String buttonStyle , Button...buttons){
        String onPressStyle = buttonStyle + "Clicked" ;
        for (Button button : buttons) {
            if (!button.getStyleClass().contains(buttonStyle)) button.getStyleClass().add(buttonStyle);

            button.setOnMousePressed(e -> {
                button.getStyleClass().add(onPressStyle);
                button.setTranslateX(-1);
                button.setTranslateY(1);
            });
            button.setOnMouseReleased(e -> {
                button.getStyleClass().remove(onPressStyle);
                button.setTranslateX(0);
                button.setTranslateY(0);
            });
        }
    }

    public static void setBackButtonOnPress(Button backButton){
        backButton.setOnAction(e -> MenuHandler.exitMenu());
        backButton.setOnMousePressed(e -> {
            backButton.setTranslateX(-5);
            backButton.setTranslateY(-5);
        });
        backButton.setOnMouseReleased(e -> {
            backButton.setTranslateX(0);
            backButton.setTranslateY(0);
        });
    }

    public static void setCellStyle(String cellStyle, Rectangle rectangle, ImageView imageView){
        String enteredStyle = cellStyle + "Entered";
        if(!rectangle.getStyleClass().contains(cellStyle)) rectangle.setStyle(cellStyle);

       imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rectangle.getStyleClass().add(enteredStyle);
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rectangle.getStyleClass().remove(enteredStyle);
            }
        });

    }


}
