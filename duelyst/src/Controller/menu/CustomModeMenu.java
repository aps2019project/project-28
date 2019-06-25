package Controller.menu;

import Controller.Game;
import Model.account.Account;
import Model.account.Deck;
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
    public Menu selectDeck(String deckName) throws InvalidDeckException {
        Deck deck=this.account.getCollection().getDeckByName(deckName);

        Account.AI[0].clearCollection();
        Account.AI[0].getCollection().forcePushDeck(deck);

        Game.accounts[1]=Account.AI[0];

        return this.enter(ChooseBattleModeMenu.getMenu());
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4) select [deck name]");
    }


    @Override
    public void selectDeck(Account account, Deck deck) {
        if (!mainDeckSelected){
            account.getCollection().setMainDeck(deck);
            mainDeckSelected = true ;
            showDeckSelector(account);
        }else{
            try {
                MenuHandler.setCurrentMenu(selectDeck(deck.getName()));
            } catch (InvalidDeckException ignored) {}
        }
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
