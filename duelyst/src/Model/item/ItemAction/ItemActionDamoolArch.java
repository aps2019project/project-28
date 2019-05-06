package Model.item.ItemAction;

import Model.Map.Cell;
import Model.item.Item;

public class ItemActionDamoolArch implements ItemAction {
    static private ItemActionDamoolArch obj;

    public static ItemActionDamoolArch getItemAction() {
        if (obj == null) obj = new ItemActionDamoolArch();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            cell.getCardOnCell().setRange(cell.getCardOnCell().getRange() + item.getPerk());
        }
    }
}