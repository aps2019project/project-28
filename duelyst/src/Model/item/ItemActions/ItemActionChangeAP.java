package Model.item.ItemActions;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.item.Item;
import exeption.InvalidCellException;

public class ItemActionChangeAP implements ItemAction {
    static private ItemActionChangeAP obj;

    public static ItemActionChangeAP getItemAction() {
        if (obj == null) obj = new ItemActionChangeAP();
        return obj;
    }

    public void deploy(Item item , Cell[] target) throws InvalidCellException {
        for (Cell cell : target) {
            Buff buff = new Buff(1 , false , BuffActionAP.getBuffAction() , 2) ;
            buff.deploy(Battle.getMenu().getPlayer() , cell.getCardOnCell());
        }
    }
}
