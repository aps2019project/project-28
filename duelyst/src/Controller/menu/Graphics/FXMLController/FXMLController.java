package Controller.menu.Graphics.FXMLController;

import Controller.menu.Menu;
import Controller.menu.SignInMenu;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

public class FXMLController {
    protected Menu menu= SignInMenu.getMenu();



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
        this.menu.getGraphic().getScene().setOnMouseEntered(e -> this.menu.getGraphic().getScene().setCursor(new ImageCursor(new Image(this.menu.getGraphic().getMousePath()))));
    }

    public void initialize(){
        init();
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
