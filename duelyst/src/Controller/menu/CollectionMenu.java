package Controller.menu;

import Model.account.Account;
import Model.account.Collection;

public class CollectionMenu extends Menu {
    private Collection tempCollection;

    public CollectionMenu(Menu parentMenu) {
        super(parentMenu);
        this.tempCollection=new Collection();
    }

    public void save (Account account){
        account.setCollection(this.tempCollection);
    }



    @Override
    public void help() {

    }
}
