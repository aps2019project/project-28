package Controller.menu;

import Controller.Game;
import Model.account.Account;
import Model.account.Deck;
import Model.account.player.AI;
import View.Listeners.OnDeckSelectorClickedListener;
import View.MenuHandler;
import exeption.*;

public class CustomModeMenu extends Menu implements DeckSelectorHavingMenu{
    private static CustomModeMenu menu;
    private OnDeckSelectorClickedListener deckSelectorListener;
    private boolean mainDeckSelected  = false ;

    private CustomModeMenu(String name) {
        super(name);
    }

    public static CustomModeMenu getMenu(){
        if(CustomModeMenu.menu==null){
            menu=new CustomModeMenu("CustomMenu");
        }
        return menu;
    }
    public void selectDeck(String deckName) throws InvalidDeckException {
        Deck deck=this.account.getCollection().getDeckByName(deckName);

        Game.accounts[1]=Account.AI[0];

        Account.AI[0].clearCollection();
        Account.AI[0].getCollection().forcePushDeck(deck);
        Account.AI[0].setPlayer(new AI(Game.accounts[1],2,2,Game.accounts[0].getPlayer()));

        MenuHandler.enterMenu(this.enter(Battle.getMenu()));
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4) select [deck name]");
    }


    @Override
    public void selectDeck(Account account, Deck deck) {
       /*account is useless*/
        try {
            selectDeck(deck.getName());
        } catch (InvalidDeckException ignored) { }
    }

    @Override
    public void setDeckSelectorListener(OnDeckSelectorClickedListener ds) {
        deckSelectorListener = ds ;
    }

    @Override
    public void showDeckSelector(Account account) {
        String title = mainDeckSelected?"Choose your opponent's Deck !":"";
        deckSelectorListener.show(account , this, title);
    }
}
