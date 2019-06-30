package View;

//import SignInMenu;
import Controller.Game;
import Controller.menu.*;
import Controller.menu.Graphics.FXMLController.*;
import Controller.menu.Battle;
import Controller.menu.MainMenu;
import Controller.menu.SignInMenu;
import Model.account.Account;
import Model.account.player.CGI;
import Model.account.player.GGI;
import Model.card.Card;
import Model.Primary;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
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

        ;
        setGIs();
        Primary.initGraphics();


        //TODO just so there is another deck you know !
        {
            Account.getAccount("a").getCollection().addNewDeck("aDeck");
            Account.getAccount("a").getCollection().getDeckByName("aDeck").addCardToDeck(Card.getCard(1));
            Account.getAccount("a").getCollection().getDeckByName("aDeck").addCardToDeck(Card.getCard("simorgh"));
//            Account.getAccount("a").getCollection().getDeckByName("aDeck").validateDeck();
        }

        configStage(primaryStage);

        //TODO -> handle exit button
        stage.setOnCloseRequest(e -> {
            try {
                stop();
            } catch (Exception ignored) {}
        });

        initializeGraphicMenu();
        MenuHandler.startMenus();
        new Thread(() -> {
            while(true){
                MenuHandler.showMenu();
                MenuHandler.nextMove();
            }
        }).start();

    }

    private void configStage(Stage primaryStage) {
        stage = primaryStage;
        primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setFullScreen(false); //TODO make it true in the end !
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
    }
    private static void initializeGraphicMenu() {
        setRootPaths();
        initGraphics();
        setListeners();
    }

    private static void setListeners() {
        SignInMenu.getMenu().addLeaderBoardClickedListener(LeaderBoardFXMLC::makeNewScene);
        MultiPlayerModeMenu.getMenu().addLeaderBoardClickedListener(LeaderBoardFXMLC::makeNewScene);
        StoryModeMenu.getMenu().setDeckSelectorListener(DeckSelectorFXMLC::makeNewScene);
        CustomModeMenu.getMenu().setDeckSelectorListener(DeckSelectorFXMLC::makeNewScene);
        MultiPlayerModeMenu.getMenu().setDeckSelectorListener(DeckSelectorFXMLC::makeNewScene);
    }

    private static void initGraphics() {
        //initializing graphics for each menu
        SignInMenu.getMenu().getGraphic().init();
        MainMenu.getMenu().getGraphic().init();
        GameModeMenu.getMenu().getGraphic().init();
        ChooseBattleModeMenu.getMenu().getGraphic().init() ;
        SinglePlayerModeMenu.getMenu().getGraphic().init();
        MultiPlayerModeMenu.getMenu().getGraphic().init();
        Battle.getMenu().getGraphic().init();
        StoryModeMenu.getMenu().getGraphic().init();
        CustomModeMenu.getMenu().getGraphic().init();
        CraftingMenu.getMenu().getGraphic().init();
        CraftingHeroMenu.getMenu().getGraphic().init();
        CraftingMinionMenu.getMenu().getGraphic().init();
        CraftingSpellMenu.getMenu().getGraphic().init();
    }


    private static void setRootPaths() {
        //setting root Path for each menu
        SignInMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/SignInMenu.fxml");
        MainMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/MainMenu.fxml");
        GameModeMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/GameModeMenu.fxml");
        SinglePlayerModeMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/SinglePlayerModeMenu.fxml");
        MultiPlayerModeMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/MultiPlayerModeMenu.fxml");
        StoryModeMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/StoryMode.fxml");
        ChooseBattleModeMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/ChooseBattleModeMenu.fxml");
        CustomModeMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/CustomModeMenu.fxml");
        ShopMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/ShopMenu.fxml");
        CollectionMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/CollectionMenu.fxml");
        CraftingMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/CraftingMenu.fxml");
        Battle.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/Battle.fxml");
        CraftingMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/CraftingMenu.fxml");
        CraftingHeroMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/CraftingHero.fxml");
        CraftingMinionMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/CraftingMinion.fxml");
        CraftingSpellMenu.getMenu().getGraphic().setRootPath("Controller/menu/Graphics/FXMLs/CraftingSpell.fxml");
    }

    public void play(String...args) {
        launch(args);
    }

    @Override
    public void setGIs() {
        Game.setUserGI(GGI.class);
        Game.setBotGI(CGI.class);
        Game.setDefaultGI(CGI.class);
    }


    public static Stage getStage() {
        return stage ;
    }

    public static Rectangle2D getPrimaryScreenBounds() {
        return primaryScreenBounds;
    }
}
