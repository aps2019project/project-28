package Model.item.ItemAction;

import Model.Map.Cell;
import Model.item.Item;

//TODO move the Action below to special powers  ! and activate it from here ! !!!!!!!
public class ItemActionMinionRandomAttacker implements ItemAction {
    static private ItemActionMinionRandomAttacker obj;

    public ItemActionMinionRandomAttacker getItemAction() {
        if (obj == null) obj = new ItemActionMinionRandomAttacker();
        return obj;
    }

    public void deploy(Item item , Cell[] target) {
        for (Cell cell : target) {
            cell.getCardOnCell().setHasTheDeathCurse(true);
        }
    }
}
