package Model.item.ItemAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.item.Item;

public class ItemActionShieldAF implements ItemAction {
    static private ItemActionShieldAF obj;

    public static ItemActionShieldAF getItemAction() {
        if (obj == null) obj = new ItemActionShieldAF();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        Buff buff = new Buff(-1, true, BuffActionHolly.getBuffAction()) ;
        Buff buff2 = new Buff(-1, true, BuffActionHolly.getBuffAction()) ;
        buff.deploy(Game.battle.getPlayer() , Game.battle.getPlayer().getDeck().getHero());
        buff2.deploy(Game.battle.getPlayer() , Game.battle.getPlayer().getDeck().getHero());
    }
}