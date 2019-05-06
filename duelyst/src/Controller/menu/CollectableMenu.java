package Controller.menu;

import Controller.Game;
import Model.item.Item;
import Model.item.OnItemDetailPresentedListener;
import exeption.InvalidCellException;

public class CollectableMenu extends Menu {

    private static CollectableMenu menu;

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

    public void showInfo() {
        Item item = this.account.getPlayer().getSelectedItem();
        for (OnItemDetailPresentedListener presenter : item.getItemDetailPresenters()) {
            presenter.showItemDetail(item);
        }
    }

    public void useItem(int x, int y) throws InvalidCellException {
        this.account.getPlayer().getSelectedItem().deploy(Game.battle.getMap().getCell(x, y));
        // TODO: 5/5/19 saE doroste dg?
    }


    @Override
    public void help() {

    }
}
