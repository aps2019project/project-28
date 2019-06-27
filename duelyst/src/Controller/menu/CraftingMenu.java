package Controller.menu;

import Model.account.Account;
import Model.account.Collection;
import Model.account.Shop;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.card.hermione.Hermione;
import Model.item.Item;
import Model.item.OnItemDetailPresentedListener;
import View.Listeners.OnCollectionPresentedListener;
import View.Listeners.OnSearchClickedListener;
import View.Listeners.OnSearchCollectionClickedListener;
import View.Listeners.OnShowClickedListener;
import exeption.*;

import java.util.ArrayList;

public class CraftingMenu extends Menu {

    private static CraftingMenu menu;

    private CraftingMenu(String name) {
        super(name);
    }

    public static CraftingMenu getMenu() {
        if (CraftingMenu.menu == null) {
            CraftingMenu.menu = new CraftingMenu("CraftingMenu");
        }
        return menu;
    }

    public void addToShop(Card h) throws CardExistException {
        Shop.getInstance().getCollection().addCardToCollection(h);
    }


    @Override
    public Menu enter(Menu subMenu) {
        return super.enter(subMenu);
    }

    @Override
    public Menu exit() {
        return super.exit();
    }
}
