package Model.item.ItemActions;

import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.item.Item;

public class ItemActionExir implements ItemAction {
    static private ItemAction30chicken obj;

    public static ItemAction30chicken getItemAction() {
        if (obj == null) obj = new ItemAction30chicken();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            Hermione card = cell.getCardOnCell() ;
            card.changeHealthPoint(item.getPerk());
            card.changeAttackPoint(item.getPerk());
        }
    }
}
