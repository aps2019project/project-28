package Model.item.ItemActions;

import Model.Map.Cell;
import Model.item.Item;

public class ItemActionjoonBaw implements ItemAction {
    static private ItemActionjoonBaw obj;

    public ItemActionjoonBaw getItemAction() {
        if (obj == null) obj = new ItemActionjoonBaw();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            cell.getCardOnCell().changeHealthPoint(item.getPerk());
        }
    }
}
