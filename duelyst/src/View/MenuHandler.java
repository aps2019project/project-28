package View;

import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.*;
//import SignInMenu;
import Controller.menu.SignInMenu;
import Model.mediator.OnlineBattleMediator;
import Model.Primary;
import Model.account.Account;
import Model.account.Shop;
import Model.mediator.*;
import network.client.Client;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class MenuHandler {

    private static Menu currentMenu;
    private static Account account;
    private static String host ;
    private static int main_port , battle_port , chat_port ;

    public static void main(String[] args) throws IOException {

//        configLocal();
        configNetwork();


        initMenus();


//        View input = new ConsoleView();
        View input = new GraphicView();

        input.play(args);
    }

    private static void configNetwork() throws IOException {

        Scanner portReader = new Scanner (new FileReader("config.txt"));
        host = portReader.nextLine();
        main_port = portReader.nextInt();
        battle_port = portReader.nextInt();
        chat_port = portReader.nextInt();

        Game.setClient(new Client());
        Game.setChatRoomClient(new Client(chat_port));


        Account.setMediator(new OnlineAccountMediator());
        SignInMenu.getMenu().setSignInMenuMediator(new OnlineSignInMenuMediator());
        Shop.getInstance().setShopMediator(new OnlineShopMediator());
        MultiPlayerModeMenu.getMenu().setMediator(new OnlineMultiPlayerMenuMediator());
        Battle.getMenu().setMediator(new OnlineBattleMediator());
        try {
            Primary.preprocess();
            Primary.generateAI();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void configLocal() {
        Account.setMediator(new OfflineAccountMediator());
        SignInMenu.getMenu().setSignInMenuMediator(new OfflineSignInMenuMediator());
        MultiPlayerModeMenu.getMenu().setMediator(new OfflineMultiPlayerMenuMediator());
        Battle.getMenu().setMediator(new OfflineBattleMediator());
        try {
            Primary.preprocess();
            Primary.configAccounts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //it is important for this line to be after Primary.preProcess()
        Shop.getInstance().setShopMediator(new OfflineShopMediator());
    }

    public static void startMenus() {
        currentMenu = SignInMenu.getMenu().enter();
    }



    public static void showMenu() {
        MenuHandler.currentMenu.showMenu();
    }
    public static void nextMove() {
        System.err.println();
        Game.getAccount(Battle.getMenu().getTurn()).getPlayer().getGI().intervene();
        try {
            currentMenu.getGraphic().getController().updateScene();
        }catch (Exception ignored){}
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }
    public static void enterMenu(Menu menu) {
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
        GameModeMenu.getMenu().addSubMenu(MultiPlayerModeMenu.getMenu());

        SinglePlayerModeMenu.getMenu().addSubMenu(StoryModeMenu.getMenu());
        SinglePlayerModeMenu.getMenu().addSubMenu(CustomModeMenu.getMenu());


        //az Single o Multi mirim gameMode

        Battle.getMenu().addSubMenu(GraveYardMenu.getMenu());
        Battle.getMenu().addSubMenu(CollectableMenu.getMenu());

        CraftingMenu.getMenu().addSubMenu(CraftingMenu.getHeroMenu());
        CraftingMenu.getMenu().addSubMenu(CraftingMenu.getMinionMenu());
        CraftingMenu.getMenu().addSubMenu(CraftingMenu.getSpellMenu());

        currentMenu = SignInMenu.getMenu();
    }

    public static void setCurrentMenu(Menu currentMenu) {
        MenuHandler.currentMenu = currentMenu;
    }

    public static int getMain_port() {
        return main_port;
    }

    public static void setMain_port(int main_port) {
        MenuHandler.main_port = main_port;
    }

    public static int getBattle_port() {
        return battle_port;
    }

    public static void setBattle_port(int battle_port) {
        MenuHandler.battle_port = battle_port;
    }

    public static int getChat_port() {
        return chat_port;
    }

    public static void setChat_port(int chat_port) {
        MenuHandler.chat_port = chat_port;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        MenuHandler.host = host;
    }
}
