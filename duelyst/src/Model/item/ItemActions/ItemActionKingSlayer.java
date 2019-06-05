package Model.item.ItemActions;

import Controller.Game;
import Controller.menu.Battle;
import Model.item.Item;

public class ItemActionKingSlayer implements ItemAction {
    static private ItemActionChangeAP obj;

    public ItemActionChangeAP getItemAction() {
        if (obj == null) obj = new ItemActionChangeAP();
        return obj;
    }

    public void deploy(Item item){
        Battle.getMenu().getPlayer().setMaxMana(Battle.getMenu().getPlayer().getMaxMana());

    }
}