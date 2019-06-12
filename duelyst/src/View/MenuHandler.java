package View;
import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.*;
import Model.Primary;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;


public class MenuHandler extends Application {

    public static Menu currentMenu;

    private static Stage stage;
    public final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    private static void initMenus() {
        //az SignIn Menu mirim tuye MainMenu

        SignInMenu.getMenu().addSubMenu(MainMenu.getMenu());

        MainMenu.getMenu().addSubMenu(CollectionMenu.getMenu());
        MainMenu.getMenu().addSubMenu(ShopMenu.getMenu());
        MainMenu.getMenu().addSubMenu(ChooseBattleModeMenu.getMenu());

        GameModeMenu.getMenu().addSubMenu(SinglePlayerModeMenu.getMenu());
        GameModeMenu.getMenu().addSubMenu(MultiPlayerModeMenu.getMenu());

        SinglePlayerModeMenu.getMenu().addSubMenu(StoryModeMenu.getMenu());
        SinglePlayerModeMenu.getMenu().addSubMenu(CostumeModeMenu.getMenu());

        //az Single o Multi mirim gameModet

        Battle.getMenu().addSubMenu(GraveYardMenu.getMenu());
        Battle.getMenu().addSubMenu(CollectableMenu.getMenu());

        currentMenu = SignInMenu.getMenu();
    }


    //moh
    public static void main(String[] args) {
        try {
            Primary.preprocess();
            Primary.pre();
            initMenus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConsoleInput consoleInput = new ConsoleInput();
        launch(args);
        consoleInput.play();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
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
        currentMenu.goToScene(primaryStage , primaryScreenBounds);
        primaryStage.show() ;

    }

    public static void showMenu() {
        MenuHandler.currentMenu.showMenu();
    }


    public static void nextMove() {
        if (MenuHandler.currentMenu instanceof Battle)
            Battle.getMenu().getPlayer().doYourMove();
    }

    public static Scanner getGameScanner() {
        return Game.accounts[Battle.getMenu().getTurn()].getPlayer().getOutputStream();
    }

    public static Stage getStage() {
        return stage;
    }
}
