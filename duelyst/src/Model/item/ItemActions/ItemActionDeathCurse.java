package Model.item.ItemActions;

import Model.Map.Cell;
import Model.item.Item;

public class ItemActionDeathCurse implements ItemAction {
    static private ItemActionDeathCurse obj;

    public static ItemActionDeathCurse getItemAction() {
        if (obj == null) obj = new ItemActionDeathCurse();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            if (cell.getCardOnCell() != null){
                cell.getCardOnCell().setHasTheDeathCurse(true);
            }
        }
    }
}
