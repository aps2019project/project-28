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
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Scanner;


public class MenuHandler {

    public static Menu currentMenu;

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

        currentMenu=SignInMenu.getMenu().enter();
//        currentMenu = SignInMenu.getMenu();
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
        GraphicInput input = GraphicInput.getGraphicInput();

//        launch(args);
        input.play(args);
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
}
