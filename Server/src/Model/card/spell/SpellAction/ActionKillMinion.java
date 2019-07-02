package Model.card.spell.SpellAction;

import Model.Map.Cell;
import Model.card.spell.Spell;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class ActionKillMinion implements Action {
    private static ActionKillMinion obj;

    public static ActionKillMinion getAction() {
        if (obj == null) obj = new ActionKillMinion();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException{
        cells[0].getCardOnCell().die();
    }
}
