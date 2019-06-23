package View.Listeners;

import Controller.menu.DeckSelectorHavingMenu;
import Model.account.Account;

import java.util.ArrayList;

public interface OnDeckSelectorClickedListener {
    void show(Account account, DeckSelectorHavingMenu menu);
}
