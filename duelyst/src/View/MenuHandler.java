package View;
import Controller.Game;
import Controller.GameMode.FlagMode;
import Controller.menu.Battle;
import Controller.menu.*;
//import SignInMenu;
import Controller.menu.SignInMenu;
import Model.Primary;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

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
//        View input =new ConsoleView();
        View input = new GraphicView();

        input.play(args);
    }

    public static void startMenus() {
        currentMenu= Battle.getMenu().enter();
    }

    public static void dirtyPeaceShit() {
        currentMenu= SignInMenu.getMenu();
        try {
            SignInMenu.getMenu().logIn( "warlord","1");
            ChooseBattleModeMenu.getMenu().setMode(1);
            System.err.println();
            StoryModeMenu.getMenu().setAI(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
