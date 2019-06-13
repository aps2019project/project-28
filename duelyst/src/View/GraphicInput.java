package View;

import Controller.menu.Menu;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GraphicInput extends Application {
    private static GraphicInput graphicInput ;

    private Stage stage;
    private Rectangle2D primaryScreenBounds ;



    public static GraphicInput getGraphicInput(){
        if (graphicInput == null){
            graphicInput = new GraphicInput() ;
        }
        return graphicInput ;
    }

    private GraphicInput() {}

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        Pane p = new Pane() ;
//        p.setStyle("-fx-background-color: BLACK");
//        Scene loadingScene = new Scene(p);
//        stage.setScene(loadingScene);

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
        primaryStage.show() ;

    }

    public void play(String...args) {
        launch(args);
    }


    public Stage getStage() {
        return stage ;
    }

    public Rectangle2D getPrimaryScreenBounds() {
        return primaryScreenBounds;
    }
}
