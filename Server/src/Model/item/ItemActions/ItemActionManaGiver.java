package Model.item.ItemActions;

import Controller.menu.Battle;
import Model.account.player.Player;
import Model.item.Item;

public class ItemActionManaGiver implements ItemAction {
    static private ItemActionManaGiver obj;

    public static ItemActionManaGiver getItemAction() {
        if (obj == null) obj = new ItemActionManaGiver();
        return obj;
    }

    public void deploy(Item item){
        Player player = Battle.getMenu().getPlayer();
        player.getStuffEffectsOnPlayer().setManaTheriac(item.getPerk());
    }
}