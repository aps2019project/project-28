package Model.item;

import Controller.Game;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;

public interface ItemAction {
    void deploy(Item item);
}

class ItemActionExtraMana implements ItemAction {
    static private ItemActionExtraMana obj;

    public ItemActionExtraMana getItemAction() {
        if (obj == null) obj = new ItemActionExtraMana();
        return obj;
    }

    public void deploy(Item item) {
        if (Game.battle.getOriginalTurn() > 2) Game.battle.getPlayer().setMana(Game.battle.getPlayer().getMana() + 1);
    }
}

class ItemActionShieldAF implements ItemAction {
    static private ItemActionShieldAF obj;

    public ItemActionShieldAF getItemAction() {
        if (obj == null) obj = new ItemActionShieldAF();
        return obj;
    }

    public void deploy(Item item) {
        Buff buff = new Buff(-1, true, BuffActionHolly.getBuffAction()) ;
        Buff buff2 = new Buff(-1, true, BuffActionHolly.getBuffAction()) ;
        buff.deploy(Game.battle.getPlayer() , );
    }
}