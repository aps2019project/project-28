package View.Listeners;

import Controller.menu.DeckSelectorHavingMenu;
import Controller.menu.Graphics.FXMLController.DeckSelectorHavingMenu2;
import Model.account.Account;
import Model.account.Deck;

import java.util.List;

public interface OnDeckSelector2ClickedListener {
    void show(List<Deck> decks, DeckSelectorHavingMenu2 menu, String title);
}
