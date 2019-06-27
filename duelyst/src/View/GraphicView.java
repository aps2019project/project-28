package View;

//import SignInMenu;
import Controller.Game;
import Controller.menu.*;
import Controller.menu.Graphics.FXMLController.DeckSelectorFXMLC;
import Controller.menu.Graphics.FXMLController.LeaderBoardFXMLC;
import Controller.menu.Battle;
import Controller.menu.MainMenu;
import Controller.menu.SignInMenu;
import Controller.menu.Graphics.FXMLController.SignInMenuFXMLC;
import Model.account.Account;
import Model.card.Card;
import View.Listeners.OnLeaderBoardClickedListener;
import Model.Primary;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;


public class GraphicView extends Application implements View{

    private static Stage stage;
    private static Rectangle2D primaryScreenBounds ;

    private CommandHandler commandHandler;


    public static void setScene(Scene scene) {
        GraphicView.stage.setScene(scene);
        stage.show();
    }

    public GraphicView(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        commandHandler.setOutputStream(System.out);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {



        Primary.initGraphics();


        //TODO just so there is another deck you know !
        {
            Account.getAccount("a").getCollection().addNewDeck("aDeck");
            Account.getAccount("a").getCollection().getDeckByName("aDeck").addCardToDeck(Card.getCard(1));
            Account.getAccount("a").getCollection().getDeckByName("aDeck").addCardToDeck(Card.getCard("simorgh"));
//            Account.getAccount("a").getCollection().getDeckByName("aDeck").validateDeck();
        }

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
        //TODO -> handle exit button
        stage.setOnCloseRequest(e -> {
            try {
                stop();
            } catch (Exception ignored) {}
        });

        initializeGraphicMenu();
        MenuHandler.startMenus();
//        primaryStage.setScene(SignInMenu.getMenu().getGraphic().getScene());
//        primaryStage.show() ;

    }

    private static void initializeGraphicMenu() {
        setRootPaths();
        initGraphics();
        setListeners();
    }

    private static void setListeners() {
        SignInMenu.getMenu().addLeaderBoardClickedListener((accounts , fxmlc) -> {
              LeaderBoardFXMLC.makeNewScene(accounts , fxmlc);
        });

        MultiPlayerModeMenu.getMenu().addLeaderBoardClickedListener((accounts , fxmlc) -> {
            LeaderBoardFXMLC.makeNewScene(accounts , fxmlc);
        });

        StoryModeMenu.getMenu().setDeckSelectorListener((account , menu , title)-> {
            DeckSelectorFXMLC.makeNewScene(account , menu, title);
        });

        CustomModeMenu.getMenu().setDeckSelectorListener((account , menu , title)-> {
            DeckSelectorFXMLC.makeNewScene(account , menu, title);
        });

        MultiPlayerModeMenu.getMenu().setDeckSelectorListener((account , menu , title)-> {
            DeckSelectorFXMLC.makeNewScene(account , menu, title);
        });
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
