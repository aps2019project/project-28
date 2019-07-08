package Controller.menu.Graphics.FXMLController;

import Model.account.Deck;
import View.Listeners.OnDeckSelector2ClickedListener;

import java.util.List;

public interface DeckSelectorHavingMenu2 {
    void selectDeck2(Deck deck);
    void setDeckSelector2Listener(OnDeckSelector2ClickedListener ds);
    void showDeckSelector2(List<Deck> decks);
}
