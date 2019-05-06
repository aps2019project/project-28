package Model.item.ItemActions;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.item.Item;

public class ItemActionAssasinationDagger implements ItemAction {
    static private ItemActionAssasinationDagger obj;

    public static ItemActionAssasinationDagger getItemAction() {
        if (obj == null) obj = new ItemActionAssasinationDagger();
        return obj;
    }

    public void deploy(Item item, Cell[] target){

    }
}