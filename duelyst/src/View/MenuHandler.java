package View;
import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.*;
//import SignInMenu;
import Controller.menu.SignInMenu;
import Model.Primary;
import Model.account.Account;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class MenuHandler {

    private static Menu currentMenu;
    private static ArrayList<Menu> lastMenus = new ArrayList<>();
    private static Account account ;
    private static CommandHandler commandHandler=new CommandHandler();

        public static void main(String[] args) {
        try {
            Primary.preprocess();
            initMenus();
        } catch (Exception e) {
            e.printStackTrace();
        }
            System.err.println("debug");
        View input = new ConsoleView();
//        View input = new GraphicView();
        input.play(args);
    }

    public static void startMenus() {
        currentMenu= SignInMenu.getMenu().enter();
    }



    public static void showMenu() {
        MenuHandler.currentMenu.showMenu();
    }

    public static void nextMove() {
        Game.accounts[Battle.getMenu().getTurn()].getPlayer().getGI().intervene();
    }

    public static void setCurrentMenu(Menu currentMenu) {
        if (MenuHandler.currentMenu != null)MenuHandler.lastMenus.add(MenuHandler.currentMenu);
        MenuHandler.currentMenu = currentMenu;
        currentMenu.enter();
    }
    public static Menu getCurrentMenu() {
        return currentMenu;
    }
    public static ArrayList<Menu> getLastMenus() {
        return lastMenus;
    }
    public static void goBack() {
        currentMenu = lastMenus.get(lastMenus.size()-1).enter();
        lastMenus.remove(lastMenus.size()-1);
    }

    public static Account getAccount() {
        return account;
    }
    public static void setAccount(Account account) {
        MenuHandler.account = account;
    }

    private static void initMenus() {
        System.err.println("debug");
        //az SignIn Menu mirim tuye MainMenu

        SignInMenu.getMenu().addSubMenu(MainMenu.getMenu());

        MainMenu.getMenu().addSubMenu(CollectionMenu.getMenu());
        MainMenu.getMenu().addSubMenu(ShopMenu.getMenu());
        MainMenu.getMenu().addSubMenu(ChooseBattleModeMenu.getMenu());

        GameModeMenu.getMenu().addSubMenu(SinglePlayerModeMenu.getMenu());
        GameModeMenu.getMenu().addSubMenu(MultiPlayerModeMenu.getMenu());

        SinglePlayerModeMenu.getMenu().addSubMenu(StoryModeMenu.getMenu());
        SinglePlayerModeMenu.getMenu().addSubMenu(CustomModeMenu.getMenu());


        //az Single o Multi mirim gameMode

        Battle.getMenu().addSubMenu(GraveYardMenu.getMenu());
        Battle.getMenu().addSubMenu(CollectableMenu.getMenu());

        currentMenu = SignInMenu.getMenu();
    }
}
