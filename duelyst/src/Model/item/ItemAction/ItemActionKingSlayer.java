package Model.item.ItemAction;

import Controller.Game;
import Model.item.Item;

public class ItemActionKingSlayer implements ItemAction {
    static private ItemActionChangeAP obj;

    public ItemActionChangeAP getItemAction() {
        if (obj == null) obj = new ItemActionChangeAP();
        return obj;
    }

    public void deploy(Item item){
        Game.battle.getPlayer().setMaxMana(Game.battle.getPlayer().getMaxMana());

    }
}