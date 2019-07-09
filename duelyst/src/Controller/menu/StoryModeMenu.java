package Controller.menu;

import Controller.Game;
import Model.account.player.AI;
import Model.account.Account;
import Model.account.Deck;
import Model.mediator.OfflineBattleMediator;
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
        Game.setSecondAccount(Account.AI[level]);
        Game.getAccount(1).setPlayer(new AI(Game.getAccount(1),2,2,Game.getAccount(0).getPlayer()));
        Battle.getMenu().setMediator(new OfflineBattleMediator());
        MenuHandler.enterMenu(Battle.getMenu());
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
