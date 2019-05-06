package Model.item.ItemActions;

import Controller.Game;
import Model.account.Player;
import Model.item.Item;

public class ItemActionManaGiver implements ItemAction {
    static private ItemActionManaGiver obj;

    public ItemActionManaGiver getItemAction() {
        if (obj == null) obj = new ItemActionManaGiver();
        return obj;
    }

    public void deploy(Item item){
        Player player = Game.battle.getPlayer();
        player.setManaTheriac(item.getPerk());
    }
}