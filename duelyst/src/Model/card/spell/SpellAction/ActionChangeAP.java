package Model.card.spell.SpellAction;

import Controller.menu.Battle;
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
            if (cell != null && cell.getCardOnCell() != null) {
                try {
                    Hermione card = cell.getCardOnCell();
                    card.changeAttackPoint(spell.getPerk(spell.getIndexOfAction(this)));

                } catch (NullPointerException e) {
                    System.err.println("nullPointerException in ActionChangeAp");
                }
            }
        }
    }
}
