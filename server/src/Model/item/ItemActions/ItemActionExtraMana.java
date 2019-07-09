package Model.item.ItemActions;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.item.Item;

public class ItemActionExtraMana implements ItemAction {
    static private ItemActionExtraMana obj;

    public static ItemActionExtraMana getItemAction() {
        if (obj == null) obj = new ItemActionExtraMana();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        if (Battle.getMenu().getOriginalTurn() > 2) Battle.getMenu().getPlayer().setMana(Battle.getMenu().getPlayer().getMana() + 1);
    }
}