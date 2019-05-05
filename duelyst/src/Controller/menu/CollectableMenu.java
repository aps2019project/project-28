package Controller.menu;

import Controller.Game;
import Model.item.Item;
import Model.item.OnItemDetailPresentedListener;
import exeption.InvalidCellException;

public class CollectableMenu extends Menu{

    public CollectableMenu(Menu parentMenu) {
        super(parentMenu);
        this.account=parentMenu.getAccount();
    }

    public void showInfo(){
        Item item=this.account.getPlayer().getSelectedItem();
        for (OnItemDetailPresentedListener presenter : item.getItemDetailPresenters()) {
            presenter.showItemDetail(item);
        }
    }

    public void useItem(int x,int y) throws InvalidCellException {
        this.account.getPlayer().getSelectedItem().deploy(Game.battle.getMap().getCell(x,y));
        // TODO: 5/5/19 saE doroste dg?
    }


    @Override
    public void help() {

    }
}