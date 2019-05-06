package View;

import Controller.Battle;
import Controller.menu.*;

public class ManuHandler {

    //preprocess
    {
        MainMenu mainMenu = new MainMenu(null, "MainMenue");
        SignInMenu signInMenu = new SignInMenu(null, "SignInMenue");
        CollectionMenu collectionMenu = new CollectionMenu(null);
        ShopMenu shopMenu = new ShopMenu(null);
        Battle battle = new Battle(null);
        ChooseBattleModeMenu chooseBattleModeMenu = new ChooseBattleModeMenu(null);
        CostumeModeMenu costumeModeMenu = new CostumeModeMenu(null);
        MultiPlayerModeMenu multiPlayerModeMenu = new MultiPlayerModeMenu(null);
        SinglePlayerModeMenu singlePlayerModeMenu = new SinglePlayerModeMenu(null);
        GraveYardMenu graveYardMenu = new GraveYardMenu(null);

    }



// TODO: 5/5/19 command select collectable(page 20) change menu from battle to collectable menu
// TODO: 5/5/19 command entergraveyard changes menu from battle to graveYard





}
