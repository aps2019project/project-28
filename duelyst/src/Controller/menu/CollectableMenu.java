package Controller.menu;

import Controller.Game;
import Model.item.Item;
import Model.item.OnItemDetailPresentedListener;
import exeption.InvalidCellException;

public class CollectableMenu extends Menu {

    private static CollectableMenu menu;

//    @Override
//    protected void buildScene() {
//        super.buildScene();
//    }

    private CollectableMenu(String name) {
        super(name);
//        this.account = parentMenu.getAccount();
    }

    public static CollectableMenu getMenu(){
        if(CollectableMenu.menu==null){
            CollectableMenu.menu=new CollectableMenu("CollectableMenu");
        }
        return menu;
    }



}
