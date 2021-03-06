package Model.card.spell.SpellAction;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionDeployHollyBuff implements Action {
    private static ActionDeployHollyBuff obj;

    public static ActionDeployHollyBuff getAction() {
        if (obj == null) obj = new ActionDeployHollyBuff();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        for (Cell cell : cells) {
            if (cell.getCardOnCell() == null) throw new InvalidCellException("empty cell");
            Buff buff = new Buff(spell.getDuration(spell.getIndexOfAction(ActionDeployHollyBuff.getAction())), true, BuffActionHolly.getBuffAction(), spell.getPerk(spell.getIndexOfAction(ActionDeployHollyBuff.getAction())));
            buff.deploy(Battle.getMenu().getPlayer(), cell.getCardOnCell());

        }
    }
}
