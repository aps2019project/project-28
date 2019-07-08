package Controller.menu;

import Model.Primary;
import Model.account.Account;
import View.Listeners.OnCollectionPresentedListener;
import View.Listeners.OnSearchClickedListener;
import View.Listeners.OnSearchCollectionClickedListener;
import View.Listeners.OnShowClickedListener;
import Model.account.Collection;
import Model.account.Shop;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.item.Item;
import View.Listeners.OnItemDetailPresentedListener;
import exeption.*;

import java.util.ArrayList;

public class ShopMenu extends Menu {

    private static ShopMenu menu;
    private Shop shop = Shop.getInstance();
    private Collection tempCollection;
    private ArrayList<OnSearchClickedListener> searchClickedListeners;
    private ArrayList<OnSearchCollectionClickedListener> searchCollectionClickedListeners;
    private ArrayList<OnShowClickedListener> showClickedListeners;

    private ShopMenu(String name) {
        super(name);
    }

    public static ShopMenu getMenu(){
        if(ShopMenu.menu==null){
            ShopMenu.menu=new ShopMenu("ShopMenu");
        }
        return menu;
    }

    @Override
    public boolean init(Menu parentMenu) {
        super.init(parentMenu);
        this.tempCollection = this.account.getCollection();
        return true;
    }

    public void showCollection() {
        for (OnCollectionPresentedListener presenter : Collection.getCollectionPresentedListeners()) {
            presenter.show(this.account.getCollection(),this.account.getName()+"'s Collection");
        }
    }

    public void search(String name) throws InvalidCardException, InvalidItemException {
        searchInGivenCollection(name, this.shop.getCollection());
    }

    public void searchCollection(String name) throws InvalidCardException, InvalidItemException {
        searchInGivenCollection(name, this.account.getCollection());
    }

    private void searchInGivenCollection(String name, Collection collection) throws InvalidCardException,
            InvalidItemException {
        if (collection.hasCard(name)) {
            Card card = collection.getCard(name);
            for (OnCardDetailsPresentedListener presenter : Card.getCardDetailsPresenters()) {
                presenter.showCardDetail(card);
            }
        } else if (collection.hasItem(name)) {
            Item item = collection.getItem(name);
            for (OnItemDetailPresentedListener presenter : Item.getItemDetailPresenters()) {
                presenter.showItemDetail(item);
            }
        }
        throw new InvalidCardException();
    }

    public void buy(String name) throws CardExistException, ItemExistExeption, InvalidCardException,
            NotEnoughMoneyException, FullCollectionException, InvalidItemException {
        System.err.println(name);
        if (!this.shop.getCollection().hasCard(name) && !this.shop.getCollection().hasItem(name)) {
            throw new InvalidCardException();
        }
        if (this.shop.getCollection().hasCard(name)) {
            if (this.shop.getCollection().getCard(name).getPrice() > this.account.getMoney()) {
                System.err.println(this.shop.getCollection().getCard(name).getPrice());
                System.err.println(this.account.getMoney());
                throw new NotEnoughMoneyException();
            }
            else {
                tempCollection.addCardToCollection(this.shop.getCollection().getCard(name));
                this.account.setMoney(this.account.getMoney() - this.shop.getCollection().getCard(name).getPrice());
            }

        }
        else if (this.shop.getCollection().hasItem(name)) {
            if (this.shop.getCollection().getItem(name).getPrice() > this.account.getMoney()) {
                throw new NotEnoughMoneyException();
            } else if (account.getCollection().getUsables().size() >= Collection.MAX_USABLES) {
                throw new FullCollectionException();
            } else {
                tempCollection.addItemToCollection((this.shop.getCollection().getItem(name)));
            }
        }
    }

    public void sell(String name) throws InvalidCardException {
        if (!this.account.getCollection().hasCard(name) && !this.account.getCollection().hasItem(name))
            throw new InvalidCardException();

        try {
            tempCollection.removeFromCollection(name);
        }catch (NullPointerException ignored){
            account.getCollection().removeFromCollection(name);
        }
        this.account.setMoney(this.account.getMoney() + this.shop.getCollection().getCard(name).getPrice());
    }

    public void show() {//shows the items and cards in shop
        for (OnCollectionPresentedListener presenter : Collection.getCollectionPresentedListeners()) {
            presenter.show(this.shop.getCollection(),"SHOP");
        }
    }

    @Override
    public Menu enter(Menu subMenu) {
        return super.enter(subMenu);
    }

    @Override
    public void exit() {
        this.save();
        super.exit();
    }

    public void save() {
        if (this.account == null) return ;
        this.account.setCollection(this.tempCollection);
        Primary.saveAccounts();
    }


    @Override
    public void help() {
        super.help();
        System.out.println("4)show collection     5)search [item name | card name]     6)search collection [item name | card name]");
        System.out.println("7)buy [card name | item name]     8)sell [card id | card id]     9)show");
    }
}
