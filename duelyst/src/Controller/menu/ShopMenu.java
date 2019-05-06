package Controller.menu;

import Model.account.Account;
import Model.account.Collection;
import Model.account.OnCollectionPresentedListener;
import Model.account.Shop;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.item.Item;
import Model.item.OnItemDetailPresentedListener;
import Model.item.Usable;
import exeption.*;

import java.util.ArrayList;

public class ShopMenu extends Menu {

    Shop shop = Shop.getInstance();
    Collection tempCollection;
    private ArrayList<OnSearchClickedListener> searchClickedListeners;
    private ArrayList<OnSearchCollectionClickedListener> searchCollectionClickedListeners;
    private ArrayList<OnShowClickedListener> showClickedListeners;

    public ShopMenu(Menu parentMenu) {
        super(parentMenu);
        this.account = parentMenu.getAccount();
        tempCollection = this.account.getCollection();
    }

    public void showCollection() {
        for (OnCollectionPresentedListener presenter : this.shop.getCollection().getCollectionPresentedListeners()) {
            presenter.show(this.account.getCollection());
        }
    }

    public void search(String name) throws InvalidCardException, InvalidItemException {
        searchInGivenCollection(name, this.shop.getCollection());
    }

    public void searchCollection(String name) throws InvalidCardException, InvalidItemException {
        searchInGivenCollection(name, this.account.getCollection());
    }

    private void searchInGivenCollection(String name, Collection collection) throws InvalidCardException, InvalidItemException {
        if (collection.hasCard(name)) {
            Card card = collection.getCard(name);
            for (OnCardDetailsPresentedListener presenter : card.getCardDetailsPresenters()) {
                presenter.showCardDetail(card);
            }
        } else if (collection.hasItem(name)) {
            Item item = collection.getItem(name);
            for (OnItemDetailPresentedListener presenter : item.getItemDetailPresenters()) {
                presenter.showItemDetail(item);
            }
        }
        throw new InvalidCardException();
    }

    public void buy(String name) throws CardExistException, ItemExistExeption, InvalidCardException, InvalidItemException, NotEnoughMoneyException, FullCollectionException {
        if (!this.shop.getCollection().hasCard(name) && !this.shop.getCollection().hasItem(name) ) {
             throw new InvalidCardException();
        }
        if(this.shop.getCollection().hasCard(name)) {
            if (this.shop.getCollection().getCard(name).getPrice() > this.account.getMoney()) {
                throw new NotEnoughMoneyException();
            } else {
                 tempCollection.addCardToCollection(this.shop.getCollection().getCard(name));
            }
        }
        if(this.shop.getCollection().hasItem(name)) {
            if (this.shop.getCollection().getItem(name).getPrice() > this.account.getMoney()) {
                throw new NotEnoughMoneyException();
            } else if (account.getCollection().getUsables().size() >= Collection.MAX_USABLES) {
                throw new FullCollectionException();
            } else {
                tempCollection.addItemToCollection((Usable) this.shop.getCollection().getItem(name));
            }
        }
    }

    public void sell(String name) throws InvalidItemException, InvalidCardException {
        if (!this.account.getCollection().hasCard(name)) {
                throw new InvalidItemException();
        } else {
                try
                tempCollection.removeCardFromCollection(tempCollection.getCard(name));
                tempCollection.removeItemFromCollection((Usable)tempCollection.getItem(name));
        }
    }

    public void show() {
        for (OnShowClickedListener presenter :
                showClickedListeners) {
            presenter.show(shop.getCollection());
        }
    }

    public void save(Account account) {
        account.setCollection(this.tempCollection);
    }

    @Override
    public void help() {

    }
}
