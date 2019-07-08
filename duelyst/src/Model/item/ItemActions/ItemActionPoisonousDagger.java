package Model.item.ItemActions;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.item.Item;
import exeption.InvalidCellException;

public class ItemActionPoisonousDagger implements ItemAction {
    static private ItemActionPoisonousDagger obj;

    public static ItemActionPoisonousDagger getItemAction() {
        if (obj == null) obj = new ItemActionPoisonousDagger();
        return obj;
    }

    public void deploy(Item item, Cell[] target) throws InvalidCellException {
       for (Cell cell : target){
           if (cell.getCardOnCell() == null) throw new InvalidCellException("itemActionPoisonousDagger") ;
           cell.getCardOnCell().getBuffEffects().setHasThePoisonousDagger(true);
       }
    }
}