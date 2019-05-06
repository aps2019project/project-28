package Model.item.ItemAction;

import Controller.Game;
import Model.Map.Cell;
import Model.item.Item;

public class ItemActionExtraMana implements ItemAction {
    static private ItemActionExtraMana obj;

    public static ItemActionExtraMana getItemAction() {
        if (obj == null) obj = new ItemActionExtraMana();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        if (Game.battle.getOriginalTurn() > 2) Game.battle.getPlayer().setMana(Game.battle.getPlayer().getMana() + 1);
    }
}