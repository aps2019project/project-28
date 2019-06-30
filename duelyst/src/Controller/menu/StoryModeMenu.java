package Controller.menu;

import Controller.Game;
import Model.account.player.AI;
import Model.account.Account;
import Model.account.Deck;
import View.Listeners.OnDeckSelectorClickedListener;
import View.MenuHandler;

public class StoryModeMenu extends Menu implements DeckSelectorHavingMenu {
    private static StoryModeMenu menu;
    private OnDeckSelectorClickedListener deckSelectorListener ;

    private StoryModeMenu(String name) {
        super(name);
    }

    public static StoryModeMenu getMenu(){
        ;
        if(StoryModeMenu.menu==null){
            StoryModeMenu.menu=new StoryModeMenu("StoryModeMenu");
        }
        return menu;
    }

    @Override
    public void help() {
        super.help();
    }

    public void   setAI(int level) {
        ;
        Game.accounts[1]= Account.AI[level];
        Game.accounts[1].setPlayer(new AI(Game.accounts[1],2,2,Game.accounts[0].getPlayer()));
        ;
        MenuHandler.setCurrentMenu(Battle.getMenu());
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
