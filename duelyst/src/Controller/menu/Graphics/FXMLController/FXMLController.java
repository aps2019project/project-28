package Controller.menu.Graphics.FXMLController;

import Controller.menu.Menu;
import Controller.menu.SignInMenu;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLController {
    protected Menu menu= SignInMenu.getMenu();
    protected Scene scene ;

    public FXMLController(Menu menu) {
        this.menu = menu;
    }

    public FXMLController(){}

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
//        this.buildScene();
//        stage.setScene(this.menu.getGraphic().getScene());
//    }

    public void buildScene(){
        this.menu.getGraphic().getScene().setOnMouseEntered(e -> this.menu.getGraphic().getScene().setCursor(new ImageCursor(new Image(this.menu.getGraphic().getMousePath()))));
    }

    public void initialize(){
        buildScene();
    }
}
