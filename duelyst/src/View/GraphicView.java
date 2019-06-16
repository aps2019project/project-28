package View;

//import SignInMenu;
import Controller.menu.SignInMenu;
import Controller.menu.Graphics.FXMLController.SignInMenuFXMLC;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GraphicView extends Application implements View{
    private static Stage stage;
    private static Rectangle2D primaryScreenBounds ;



    public static void setScene(Scene scene) {
        GraphicView.stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Pane p = new Pane() ;
        p.setStyle("-fx-background-color: BLACK");
        Scene loadingScene = new Scene(p);
        stage.setScene(loadingScene);

        stage.setFullScreen(false); //TODO make it true in the end !
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
//        stage.setOnCloseRequest(Event::consume) ; //TODO -> handle exit button
        stage.setOnCloseRequest(e -> {
            try {
                stop();
            } catch (Exception ignored) {}
        });

        initializeGraphicMenu();
        MenuHandler.startMenus();
//        primaryStage.setScene(SignInMenu.getMenu().getGraphic().getScene());
//
//        primaryStage.show() ;

    }

    private static void initializeGraphicMenu() {
        setRootPaths();
//        setFXMLControllers();
        initGraphics();
    }

    private static void initGraphics() {
        //initializing graphics for each menu
        SignInMenu.getMenu().getGraphic().init();
    }

//    private static void setFXMLControllers() {
//        //setting FXMLController for each menu
//        SignInMenu.getMenu().getGraphic().setController(new SignInMenuFXMLC(SignInMenu.getMenu()));
//    }

    private static void setRootPaths() {
        //setting root Path for each menu
        SignInMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/SignInMenu.fxml");
    }

    public void play(String...args) {
        launch(args);
    }


    public static Stage getStage() {
        return stage ;
    }

    public static Rectangle2D getPrimaryScreenBounds() {
        return primaryScreenBounds;
    }
}
