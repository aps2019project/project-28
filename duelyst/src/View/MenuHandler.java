package View;

import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.*;
//import SignInMenu;
import Controller.menu.SignInMenu;
import Model.Primary;
import Model.account.Account;
import Model.mediator.LocalSignInMenuMediator;
import Model.mediator.LocalAccountMediator;
import Model.mediator.NetAccountMediator;
import Model.mediator.NetSignInMenuMediator;
import network.client.Client;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;


public class MenuHandler {

    private static Menu currentMenu;
    private static ArrayList<Menu> lastMenus = new ArrayList<>();
    private static Account account;

    public static void main(String[] args) throws IOException {

//        configLocal();
        configNetwork();


        initMenus();


        View input = new ConsoleView();
//        View input = new GraphicView();

        input.play(args);
    }

    private static void configNetwork() throws IOException {
        // TODO: 7/2/19 bayad beshe network Mediator
        Account.setAccountMediator(new NetAccountMediator());
        SignInMenu.getMenu().setSignInMenuMediator(new NetSignInMenuMediator());

        Game.setClient(new Client());

        try {
            Primary.preprocess();
            Primary.generateAI();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void configLocal() {
        Account.setAccountMediator(new LocalAccountMediator());
        SignInMenu.getMenu().setSignInMenuMediator(new LocalSignInMenuMediator());
        try {
            Primary.preprocess();
            Primary.configAccouts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startMenus() {
        currentMenu = SignInMenu.getMenu().enter();
    }
    public static void showMenu() {
        MenuHandler.currentMenu.showMenu();
    }
    public static void nextMove() {
        Game.getAccount(Battle.getMenu().getTurn()).getPlayer().getGI().intervene();
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }
    public static void enterMenu(Menu menu) {
        if (MenuHandler.currentMenu != null) MenuHandler.lastMenus.add(MenuHandler.currentMenu);
        currentMenu = currentMenu.enter(menu);
    }
    public static void exitMenu() {
        MenuHandler.currentMenu.exit();
    }

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        MenuHandler.account = account;
    }

    private static void initMenus() {
        //az SignIn Menu mirim tuye MainMenu

        SignInMenu.getMenu().addSubMenu(MainMenu.getMenu());

        MainMenu.getMenu().addSubMenu(CollectionMenu.getMenu());
        MainMenu.getMenu().addSubMenu(ShopMenu.getMenu());
        MainMenu.getMenu().addSubMenu(ChooseBattleModeMenu.getMenu());

        GameModeMenu.getMenu().addSubMenu(SinglePlayerModeMenu.getMenu());
        SinglePlayerModeMenu.getMenu().addSubMenu(StoryModeMenu.getMenu());
        GameModeMenu.getMenu().addSubMenu(MultiPlayerModeMenu.getMenu());

        SinglePlayerModeMenu.getMenu().addSubMenu(CustomModeMenu.getMenu());


        //az Single o Multi mirim gameMode

        Battle.getMenu().addSubMenu(GraveYardMenu.getMenu());
        Battle.getMenu().addSubMenu(CollectableMenu.getMenu());

        CraftingMenu.getMenu().addSubMenu(CraftingMenu.getHeroMenu());
        CraftingMenu.getMenu().addSubMenu(CraftingMenu.getMinionMenu());
        CraftingMenu.getMenu().addSubMenu(CraftingMenu.getSpellMenu());

        currentMenu = SignInMenu.getMenu();
    }
}
