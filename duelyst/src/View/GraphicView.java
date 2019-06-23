package View;

//import SignInMenu;
import Controller.menu.*;
import Controller.menu.Graphics.FXMLController.LeaderBoardFXMLC;
import Controller.menu.Graphics.FXMLController.SignInMenuFXMLC;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.Main;

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
        initGraphics();
        setListeners();
    }

    private static void setListeners() {
        SignInMenu.getMenu().addLeaderBoardClickedListener(LeaderBoardFXMLC.getLeaderBoard());
    }

    private static void initGraphics() {
        //initializing graphics for each menu
        SignInMenu.getMenu().getGraphic().init();
        MainMenu.getMenu().getGraphic().init();
        ChooseBattleModeMenu.getMenu().getGraphic().init();
        SinglePlayerModeMenu.getMenu().getGraphic().init();
        MultiPlayerModeMenu.getMenu().getGraphic().init();
    }


    private static void setRootPaths() {
        //setting root Path for each menu
        SignInMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/SignInMenu.fxml");
        LeaderBoardFXMLC.getLeaderBoard().setRootPath("Controller/menu/Graphics/FXMLs/LeaderBoard.fxml");
        MainMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/MainMenu.fxml");
        ChooseBattleModeMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/ChooseBattleMode.fxml");
        SinglePlayerModeMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/SinglePlayerModeMenuFXMLC.fxml");
        SinglePlayerModeMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/MultiPlayerModeMenuFXMLC.fxml");
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
