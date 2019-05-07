package Model.item.ItemActions;

import Model.Map.Cell;
import Model.item.Item;

public class ItemAction3HornedArrow implements ItemAction {
    static private ItemAction3HornedArrow obj;

    public static ItemAction3HornedArrow getItemAction() {
        if (obj == null) obj = new ItemAction3HornedArrow();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            cell.getCardOnCell().changeAttackPoint(item.getPerk());
        }
    }
}
