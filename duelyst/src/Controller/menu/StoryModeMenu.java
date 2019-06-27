package Controller.menu;

import Controller.Game;
import Model.account.AI;
import Model.account.Account;
import Model.account.Deck;
import View.Listeners.OnDeckSelectorClickedListener;
import exeption.*;

public class StoryModeMenu extends Menu implements DeckSelectorHavingMenu {
    private static StoryModeMenu menu;
    private OnDeckSelectorClickedListener deckSelectorListener ;

    private StoryModeMenu(String name) {
        super(name);
    }

    public static StoryModeMenu getMenu(){
        System.err.println("debug");
        if(StoryModeMenu.menu==null){
            StoryModeMenu.menu=new StoryModeMenu("StoryModeMenu");
        }
        return menu;
    }

    @Override
    public void help() {
        super.help();
    }

    public Menu  setAI(int level) {
        System.err.println("debug");
        Game.accounts[1]= Account.AI[level];
        Game.accounts[1].setPlayer(new AI(Game.accounts[1],2,2,Game.accounts[0].getPlayer()));
        return this.enter(Battle.getMenu());
    }

    @Override
    public void selectDeck(Account account, Deck deck) {
        account.getCollection().setMainDeck(deck);

        this.enter(Battle.getMenu());
    }

    public void setDeckSelectorListener(OnDeckSelectorClickedListener ds){
        deckSelectorListener = ds ;
    }
    public void showDeckSelector(Account account){
        deckSelectorListener.show(account , getMenu() , "");
    }

}
