package Model.item.ItemAction;

import Model.Map.Cell;
import Model.item.Item;

public class ItemAction30chicken implements ItemAction {
    static private ItemAction30chicken obj;

    public ItemAction30chicken getItemAction() {
        if (obj == null) obj = new ItemAction30chicken();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            if (cell.getCardOnCell().getHealthPoint() < 15){
                cell.getCardOnCell().setHealthPoint(cell.getCardOnCell().getHealthPoint() * 2);
            }
        }
    }
}
