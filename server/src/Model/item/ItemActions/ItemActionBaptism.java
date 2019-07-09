package Model.item.ItemActions;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.item.Item;

public class ItemActionBaptism implements ItemAction {
    static private ItemAction30chicken obj;

    public static ItemAction30chicken getItemAction() {
        if (obj == null) obj = new ItemAction30chicken();
        return obj;
    }

    public void deploy(Item item, Cell[] ignored){
        Battle.getMenu().getPlayer().getStuffEffectsOnPlayer().setBaptism(true);
    }
}
