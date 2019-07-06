package Model.card.spell.SpellAction;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHP;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionChangeHP implements Action {
    private static ActionChangeHP obj;

    public static ActionChangeHP getAction() {
        if (obj == null) obj = new ActionChangeHP();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        for (Cell cell : cells) {
            if (cell != null && cell.getCardOnCell() != null) {
                try {
                    Hermione card = cell.getCardOnCell();
                    card.changeHealthPoint(spell.getPerk(spell.getIndexOfAction(this)));
                } catch (NullPointerException e) {
                    System.err.println("nullPointerException in ActionChangeHP");
                }
            }
        }
    }
}
