package Model.item.ItemActions;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.item.Item;

public class ItemActionRooEnTan implements ItemAction {
    static private ItemActionRooEnTan obj;

    public ItemActionRooEnTan getItemAction() {
        if (obj == null) obj = new ItemActionRooEnTan();
        return obj;
    }

    public void deploy(Item item , Cell[] target){
        for (Cell cell : target) {
            Buff buff = new Buff (2 , true , BuffActionHolly.getBuffAction() , 10) ;
            buff.deploy(Game.battle.getPlayer() , cell.getCardOnCell());
        }
    }
}