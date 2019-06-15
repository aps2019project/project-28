package Model.item.ItemActions;

import Model.Map.Cell;
import Model.card.hermione.Melee;
import Model.item.Item;

public class ItemAction2HornedArrow implements ItemAction {
    static private ItemAction2HornedArrow obj;

    public static ItemAction2HornedArrow getItemAction() {
        if (obj == null) obj = new ItemAction2HornedArrow();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            if (cell.getCardOnCell() != null && !(cell.getCardOnCell().getAttackType() instanceof Melee ))
                cell.getCardOnCell().changeAttackPoint(item.getPerk());
        }
    }
}
