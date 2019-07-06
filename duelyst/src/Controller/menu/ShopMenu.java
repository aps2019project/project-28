package Controller.menu;

import Model.account.Account;
import View.Listeners.OnCollectionPresentedListener;
import View.Listeners.OnSearchClickedListener;
import View.Listeners.OnSearchCollectionClickedListener;
import View.Listeners.OnShowClickedListener;
import Model.account.Collection;
import Model.account.Shop;
import exeption.*;

import java.util.ArrayList;

public class ShopMenu extends Menu {

    private static ShopMenu menu;
    Shop shop = Shop.getInstance();
    Collection tempCollection;
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
        tempCollection = this.account.getCollection();
        return true;
    }

    public void showCollection() {
        for (OnCollectionPresentedListener presenter : Collection.getCollectionPresentedListeners()) {
            presenter.show(this.account.getCollection(),this.account.getName()+"'s Collection");
        }
    }

    public void search(String name) throws InvalidCardException, InvalidItemException {
        this.shop.search(name);
    }

    public void searchCollection(String name) throws InvalidCardException, InvalidItemException {
        this.account.getCollection().search(name);
    }



    public void buy(String name) throws CardExistException, ItemExistExeption, InvalidCardException,
            NotEnoughMoneyException, FullCollectionException, InvalidItemException {

        if (!this.shop.hasCard(name) && !this.shop.hasItem(name)) {
            throw new InvalidCardException();
        }
        if (this.shop.hasCard(name)) {
            if (this.shop.getCard(name).getPrice() > this.account.getMoney()) {
                throw new NotEnoughMoneyException();
            }
            else {
                tempCollection.addCardToCollection(this.shop.getCard(name));
                this.account.setMoney(this.account.getMoney() - this.shop.getCard(name).getPrice());
            }
        }
        else if (this.shop.hasItem(name)) {
            if (this.shop.getItem(name).getPrice() > this.account.getMoney()) {
                throw new NotEnoughMoneyException();
            } else if (account.getCollection().getUsables().size() >= Collection.MAX_USABLES) {
                throw new FullCollectionException();
            } else {
                tempCollection.addItemToCollection((this.shop.getItem(name)));
            }
        }
        this.shop.buy(name);
    }

    public void sell(String name) throws InvalidCardException, InvalidItemException {
        if (!this.account.getCollection().hasCard(name) && !this.account.getCollection().hasItem(name))
            throw new InvalidCardException();
        tempCollection.removeFromCollection(name);
        this.account.setMoney(this.account.getMoney() + this.shop.getCard(name).getPrice());
        this.shop.sell(name);
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
    public Menu exit() {
        this.save();
        return super.exit();
    }

    public void save() {
        this.account.setCollection(this.tempCollection);
        Account.save();
    }


    @Override
    public void help() {
        super.help();
        System.out.println("4)show collection     5)search [item name | card name]     6)search collection [item name | card name]");
        System.out.println("7)buy [card name | item name]     8)sell [card id | card id]     9)show");
    }
}
