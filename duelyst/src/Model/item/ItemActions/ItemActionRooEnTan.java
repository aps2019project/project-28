package Model.item.ItemActions;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.item.Item;
import exeption.InvalidCellException;

public class ItemActionRooEnTan implements ItemAction {
    static private ItemActionRooEnTan obj;

    public static ItemActionRooEnTan getItemAction() {
        if (obj == null) obj = new ItemActionRooEnTan();
        return obj;
    }

    public void deploy(Item item , Cell[] target) throws InvalidCellException {
        for (Cell cell : target) {
            Buff buff = new Buff (2 , true , BuffActionHolly.getBuffAction() , 10) ;
            buff.deploy(Battle.getMenu().getPlayer() , cell.getCardOnCell());
        }
    }
}