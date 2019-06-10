package Controller.menu;

import Controller.Game;
import Model.account.AI;
import Model.account.Account;
import Model.account.Deck;
import exeption.*;

public class CostumeModeMenu extends Menu {
    private static CostumeModeMenu menu;
    private CostumeModeMenu(String name) {
        super(name);
    }


    @Override
    protected void buildScene() {
        super.buildScene();
    }

    public static CostumeModeMenu getMenu(){
        if(CostumeModeMenu.menu==null){
            menu=new CostumeModeMenu("CostumeMenu");
        }
        return menu;
    }
    public Menu selectDeck(String deckName) throws InvalidDeckException {
        Deck deck=this.account.getCollection().getDeckByName(deckName);

        Account.AI[0].clearCollection();
        Account.AI[0].getCollection().forcePushDeck(deck);

        Game.accounts[1]=Account.AI[0];

        System.err.println("debug");
        return this.enter(Battle.getMenu());
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4) select [deck name]");
    }
}
