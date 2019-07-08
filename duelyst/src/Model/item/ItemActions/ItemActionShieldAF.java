package Model.item.ItemActions;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.item.Item;
import exeption.InvalidCellException;

public class ItemActionShieldAF implements ItemAction {
    static private ItemActionShieldAF obj;

    public static ItemActionShieldAF getItemAction() {
        if (obj == null) obj = new ItemActionShieldAF();
        return obj;
    }

    public void deploy(Item item, Cell[] target) throws InvalidCellException {
        Buff buff = new Buff(-1, true, BuffActionHolly.getBuffAction()) ;
        Buff buff2 = new Buff(-1, true, BuffActionHolly.getBuffAction()) ;
        buff.deploy(Battle.getMenu().getPlayer() , Battle.getMenu().getPlayer().getDeck().getHero());
        buff2.deploy(Battle.getMenu().getPlayer() , Battle.getMenu().getPlayer().getDeck().getHero());
    }
}