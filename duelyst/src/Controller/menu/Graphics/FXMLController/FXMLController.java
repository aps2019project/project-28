package Controller.menu.Graphics.FXMLController;

import Controller.menu.Menu;
import View.MenuHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;


public class FXMLController {
    protected Menu menu;
//    private void goToScene(Stage stage , Rectangle2D bounds){
//        if (this.menu.getGraphic().getStage() == null) this.menu.getGraphic().setStage(stage) ;
//        if (this.menu.getGraphic().getBounds() == null) this.menu.getGraphic().setBounds(bounds) ;
//        if (this.menu.getGraphic().getScene() == null) {
//            try {
//                System.out.println(this.menu.getGraphic().getRootPath());
//                this.menu.getGraphic().setRoot( FXMLLoader.load(getClass().getResource(this.menu.getGraphic().getRootPath())));
//            }catch (IOException ignored) {
//                System.err.println("couldn't load the fxml file");
//            }
//            this.menu.getGraphic().setScene(new Scene(this.menu.getGraphic().getRoot(), bounds.getWidth(), bounds.getHeight()));
//        }
//        this.init();
//        stage.setScene(this.menu.getGraphic().getScene());
//    }

    public void init(){
        /*
        *only called once the FXML is being loaded
        *
        * very few configuration that only concerns the FXMLController and its attribute
        * note that no field is set yet so you may not use them
        * you can only set them
        *
        * not that scenes objects (such as button and labels and....) should not be used in this method cuz they are not
        * yet set
        *
        * if you wish to work with them use the method build scene
        * */

    }


    public void buildScene(){
        /*
        * only called once after the FXML is fully loaded
        *
        * use this method to work with the scenes objects (such as button ,label and ....)
        * */
        this.menu.getGraphic().getScene().setOnMouseEntered(e -> this.menu.getGraphic().getScene().setCursor(new ImageCursor(new Image(this.menu.getGraphic().getMousePath()))));
        this.menu.getGraphic().getScene().setOnMouseMoved(e -> this.menu.getGraphic().getScene().setCursor(new ImageCursor(new Image(this.menu.getGraphic().getMousePath()))));
        this.menu.getGraphic().getScene().setOnMouseClicked(e -> this.menu.getGraphic().getScene().setCursor(new ImageCursor(new Image(this.menu.getGraphic().getMousePath()))));

        menu.getGraphic().getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE){
                menu.exit();
            }
        });
    }

    public void enterScene(){

    }

    public void updateScene(){
        /*
        * called when the current menu is changed to this (every time we enter the menu)
        *
        * use this method for the update you wish to make
        *
        * note that due to your code this method can be used in other functions as well
        * */
    }

    public void initialize(){
        init();
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
