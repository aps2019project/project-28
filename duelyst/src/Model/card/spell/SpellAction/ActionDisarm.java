package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionDisarm;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionDisarm implements Action {
    private static ActionDisarm obj;

    public static ActionDisarm getAction() {
        if (obj == null) obj = new ActionDisarm();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        for (Cell cell : cells) {
            Buff buff = new Buff(spell.getDuration(spell.getIndexOfAction(ActionDisarm.getAction())), false, BuffActionDisarm.getBuffAction());
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}
