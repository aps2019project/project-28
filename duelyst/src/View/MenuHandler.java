package View;
import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.*;
import Model.Primary;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;


public class MenuHandler extends Application {

    public static Menu currentMenu;

    public static Stage stage ;

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
        } catch (Exception e) {e.printStackTrace();}
        ConsoleInput consoleInput = new ConsoleInput();
        launch(args);
        consoleInput.play();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage ;
        Scene scene = currentMenu.getMenuScene() ;
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show() ;
        stage.setResizable(false);
    }


    public static void showMenu(){
        MenuHandler.currentMenu.showMenu();
    }


    public static void nextMove() {
        if(MenuHandler.currentMenu instanceof Battle)
            Battle.getMenu().getPlayer().doYourMove();
    }

    public static Scanner getGameScanner() {
        return Game.accounts[Battle.getMenu().getTurn()].getPlayer().getOutputStream();
    }
}
