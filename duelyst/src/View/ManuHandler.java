package View;

import Controller.menu.Battle;
import Controller.menu.*;


// TODO: 5/5/19 command select collectable(page 20) change menu from battle to collectable menu
// TODO: 5/5/19 command entergraveyard changes menu from battle to graveYard


public class ManuHandler {

    private Menu currentMenu;

    //preprocess
    {
        Menu gameMode = new Menu(null,"gameMode") {
            @Override
            public void help() {
            }
        };
        MainMenu mainMenu = new MainMenu(null, "MainMenue");
        SignInMenu signInMenu = new SignInMenu(null, "SignInMenue");
        CollectionMenu collectionMenu = new CollectionMenu(null, "CollectionMenue");
        CollectableMenu collectableMenu  = new CollectableMenu(null, "CollectableMenue");
        ShopMenu shopMenu = new ShopMenu(null, "ShopMenue");
        Battle battle = new Battle(null, "Battle");
        ChooseBattleModeMenu chooseBattleModeMenu = new ChooseBattleModeMenu(null, "ChooseBattleMenu");
        CostumeModeMenu costumeModeMenu = new CostumeModeMenu(null, "CustomeModeMenu");
        MultiPlayerModeMenu multiPlayerModeMenu = new MultiPlayerModeMenu(null, "MultiPlayerModeMenu");
        SinglePlayerModeMenu singlePlayerModeMenu = new SinglePlayerModeMenu(null, "SinglePlayerModeMenu");
        GraveYardMenu graveYardMenu = new GraveYardMenu(null, "GraveYardMenue");
        StoryModeMenu storyModeMenu = new StoryModeMenu(null, "StoryModeMenu");

        //az SignIn Menu mirim tuye MainMenu

        mainMenu.addSubMenu(collectionMenu);
        mainMenu.addSubMenu(shopMenu);
        mainMenu.addSubMenu(chooseBattleModeMenu);

        chooseBattleModeMenu.addSubMenu(singlePlayerModeMenu);
        chooseBattleModeMenu.addSubMenu(multiPlayerModeMenu);

        singlePlayerModeMenu.addSubMenu(storyModeMenu);
        singlePlayerModeMenu.addSubMenu(costumeModeMenu);

        //az Single o Multi mirim gameMode

        battle.addSubMenu(graveYardMenu);
        battle.addSubMenu(collectionMenu);
        battle.addSubMenu(collectableMenu);

        currentMenu = signInMenu;
    }

    public static void main(String[] args) {

    }
}
