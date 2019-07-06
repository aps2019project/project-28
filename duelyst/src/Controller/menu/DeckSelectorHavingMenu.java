package Controller.menu;

import Model.account.Account;
import Model.account.Deck;
import View.Listeners.OnDeckSelectorClickedListener;

public interface DeckSelectorHavingMenu {
    default void selectDeck(Account account , Deck deck){
        account.getCollection().setMainDeck(deck);
    }
    void setDeckSelectorListener(OnDeckSelectorClickedListener ds);
    void showDeckSelector(Account account);
}
