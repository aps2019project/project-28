package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.card.spell.Spell;

public class ActionGhazaBokhor implements Action {
    private static ActionGhazaBokhor obj;

    public static ActionGhazaBokhor getAction() {
        if (obj == null) obj = new ActionGhazaBokhor();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            Buff buff = new Buff(-1, true, BuffActionAP.getBuffAction() , cell.getCardOnCell().getHealthPoint());
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}
