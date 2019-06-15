package Controller.menu.Graphics;

import Controller.menu.Graphics.FXMLController.FXMLController;
import Controller.menu.Menu;
import View.GraphicView;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import stuff.Resources;

import java.io.IOException;
import java.util.Objects;

public class MenuGraphics {

    private Menu menu;

    private Stage stage ;
    private Scene scene ;

    private Parent root ;
    private String rootPath ;

    private Rectangle2D bounds ;

    private String mousePath = Resources.mouse_auto.getPath();

    private FXMLController controller;


    public MenuGraphics(Menu menu) {
        this.menu = menu;
    }

    public void init(){
        this.stage= GraphicView.getStage() ;
        this.bounds=GraphicView.getPrimaryScreenBounds();
        this.scene=new Scene(new Group(), this.bounds.getWidth(), this.bounds.getHeight());

        try {
            System.out.println(this.menu.getGraphic().getRootPath());
            this.root= FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(this.rootPath)));
        }catch (IOException ignored) {
            ignored.printStackTrace();
            System.err.println("couldn't load the fxml file");
        }
        this.scene.setRoot(this.root);
        this.controller.buildScene();
    }













    public void setController(FXMLController controller) {
        this.controller = controller;

    }
    public Menu getMenu() {
        return menu;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public Scene getScene() {
        return scene;
    }
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    public Parent getRoot() {
        return root;
    }
    public void setRoot(Parent root) {
        this.root = root;
    }
    public String getRootPath() {
        return rootPath;
    }
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
    public Rectangle2D getBounds() {
        return bounds;
    }
    public void setBounds(Rectangle2D bounds) {
        this.bounds = bounds;
    }
    public String getMousePath() {
        return mousePath;
    }
    public void setMousePath(String mousePath) {
        this.mousePath = mousePath;
    }
    public FXMLController getController() {
        return controller;
    }
}
