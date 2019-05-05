package Controller.menu;

import Model.account.Account;
import Model.account.Collection;
import exeption.*;

import java.util.ArrayList;

public class ShopMenu extends Menu {

    Shop shop;
    Collection tempCollection;
    private ArrayList<OnCollectionPresenterListener> collectionPresenterListeners;
    private ArrayList<OnSearchClickedListener> searchClickedListeners;
    private ArrayList<OnSearchCollectionClickedListener> searchCollectionClickedListeners;
    private ArrayList<OnShowClickedListener> showClickedListeners;

    public ShopMenu(Menu parentMenu){
        super(parentMenu);
        this.account=parentMenu.getAccount();
        tempCollection = this.account.getCollection();
    }

    public void showCollection() {
        for (OnCollectionPresenterListener presenter:
                collectionPresenterListeners){
            presenter.show(this.account.getCollection());
        }
    }

    public void search(String name){
        if(Shop.getCollection().hasCard(name) || Shop.getCollection().hasItem(name)){
            for (OnSearchClickedListener presenter:
                 searchClickedListeners) {
                presenter.show(name);
            }
        }
    }

    public void searchCollection(String name){
        if(this.account.getCollection().hasCard(name) || this.account.getCollection().hasItem(name)){
            for (OnSearchCollectionClickedListener presenter:
                 searchCollectionClickedListeners) {
                presenter.show(this.account.getCollection());
            }
        }
    }

    public void buy(String name) throws CardExistException, ItemExistExeption, InvalidCardException, InvalidItemException {
        if(!Shop.getCollection().hasCard(name)){

        }
        if(Shop.getCollection().getCard(name).getPrice() > this.account.getMoney()){

        }
        if(account.getCollection().getUsables().size() + 1 > Collection.MAX_USABLES){

        }
        else {

            if(shop.getCollection().hasCard(name)) {
                tempCollection.addCardToCollection(tempCollection.getCard(name));
            }
            else {
                tempCollection.addItemToCollection(tempCollection.getItem(name));
            }
            //movafaghiat
        }
    }

    public void sell(String name) throws InvalidItemException, InvalidCardException, ItemDoesntExistException,
            CardDeoesntExistException{
        if(!this.account.getCollection().hasCard(name)){

        }
        else{
            if(shop.getCollection().hasCard(name)) {
                tempCollection.removeCardFromCollection(tempCollection.getCard(name));
            }
            else {
                tempCollection.removeItemFromCollection(tempCollection.getItem(name));
            }

            //movafagh
        }
    }

    public void show(){
        for (OnShowClickedListener presenter:
             showClickedListeners) {
            presenter.show(shop.getCollection());
        }
    }

    public void save (Account account){
        account.setCollection(this.tempCollection);
    }

    @Override
    public void help() {

    }
}
