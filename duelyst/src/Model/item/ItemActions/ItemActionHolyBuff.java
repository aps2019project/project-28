package Model.item.ItemActions;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.item.Item;
import exeption.InvalidCellException;

public class ItemActionHolyBuff implements ItemAction {
    static private ItemActionHolyBuff obj;

    public static ItemActionHolyBuff getItemAction() {
        if (obj == null) obj = new ItemActionHolyBuff();
        return obj;
    }

    public void deploy(Item item , Cell[] target) throws InvalidCellException {
        for (Cell cell : target) {
            Buff buff = new Buff (2 , true , BuffActionHolly.getBuffAction() , item.getPerk()) ;
            buff.deploy(Battle.getMenu().getPlayer() , cell.getCardOnCell());
        }
    }
}