package View.Listeners;

import Controller.menu.DeckSelectorHavingMenu;
import Model.account.Account;

public interface OnDeckSelectorClickedListener {
    void show(Account account, DeckSelectorHavingMenu menu , String title , boolean checkHero);
}
