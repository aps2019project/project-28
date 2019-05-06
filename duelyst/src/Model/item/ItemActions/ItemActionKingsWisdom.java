package Model.item.ItemActions;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.item.Item;

public class ItemActionKingsWisdom implements ItemAction {
    static private ItemActionKingsWisdom obj;

    public static ItemActionKingsWisdom getItemAction() {
        if (obj == null) obj = new ItemActionKingsWisdom();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        Game.battle.getPlayer().setMaxManaTheriac(item.getPerk());
    }
}