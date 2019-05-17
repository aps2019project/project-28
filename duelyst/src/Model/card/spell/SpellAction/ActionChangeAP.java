package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionChangeAP implements Action {
    private static ActionChangeAP obj;

    public static ActionChangeAP getAction() {
        if (obj == null) obj = new ActionChangeAP();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        for (Cell cell : cells) {
            Hermione card = cell.getCardOnCell();
            Buff buff = new Buff(spell.getDuration(spell.getIndexOfAction(ActionChangeAP.getAction())), spell.getPerk(spell.getIndexOfAction(ActionChangeAP.getAction())) > 0, BuffActionAP.getBuffAction());
            if (cell.getCardOnCell() == null) throw new InvalidCellException();
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}
