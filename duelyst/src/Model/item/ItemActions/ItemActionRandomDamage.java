package Model.item.ItemActions;

import Model.Map.Cell;
import Model.item.Item;

public class ItemActionRandomDamage implements ItemAction {
    static private ItemActionRandomDamage obj;

    public ItemActionRandomDamage getItemAction() {
        if (obj == null) obj = new ItemActionRandomDamage();
        return obj;
    }

    public void deploy(Item item , Cell[] target){
        for (Cell cell : target) {
            cell.getCardOnCell().changeHealthPoint(item.getPerk());
        }
    }
}