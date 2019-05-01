package Controller.menu;

import Controller.Game;
import Model.account.Account;
import Model.account.Collection;
import Model.card.Card;
import Model.item.Item;

import java.util.ArrayList;

public class CollectionMenu extends Menu {
    private Collection tempCollection;
    private ArrayList<OnCollectionPresentedListener>collectionPresenters;

    public CollectionMenu(Menu parentMenu) {
        super(parentMenu);
        this.tempCollection=new Collection();
        this.collectionPresenters=new ArrayList<>();
    }

    public void save (Account account){
        account.setCollection(this.tempCollection);
    }

    public void showCollection(){
        for (OnCollectionPresentedListener presenter : this.collectionPresenters) {
            presenter.showCollection(this.account.getCollection());
        }
    }

    public boolean search(String name){
        Collection collection=this.account.getCollection();
        Card card= Card.getCard();
        Item item= Item.hasItem()
    }

    public void addCollectionPresentedListener(OnCollectionPresentedListener presenter){
        this.collectionPresenters.add(presenter);
    }

    @Override
    public void help() {

    }
}
