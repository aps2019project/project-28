package View;

import Controller.Battle;
import Controller.menu.*;

public class ManuHandler {

    //preprocess
    {
        MainMenu mainMenu = new MainMenu(null, "MainMenue");
        SignInMenu signInMenu = new SignInMenu(null, "SignInMenue");
        CollectionMenu collectionMenu = new CollectionMenu(null, "CollectionMenue");
        ShopMenu shopMenu = new ShopMenu(null, "ShopMenue");
        Battle battle = new Battle(null, "Battle");
        ChooseBattleModeMenu chooseBattleModeMenu = new ChooseBattleModeMenu(null, "ChooseBattleMenu");
        CostumeModeMenu costumeModeMenu = new CostumeModeMenu(null, "CustomeModeMenu");
        MultiPlayerModeMenu multiPlayerModeMenu = new MultiPlayerModeMenu(null, "MultiPlayerModeMenu");
        SinglePlayerModeMenu singlePlayerModeMenu = new SinglePlayerModeMenu(null, "SinglePlayerModeMenu");
        GraveYardMenu graveYardMenu = new GraveYardMenu(null, "GraveYardMenue");

    }




// TODO: 5/5/19 command select collectable(page 20) change menu from battle to collectable menu
// TODO: 5/5/19 command entergraveyard changes menu from battle to graveYard





}
