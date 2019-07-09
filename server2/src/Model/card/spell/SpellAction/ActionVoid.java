package Model.card.spell.SpellAction;

import Model.Map.Cell;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionVoid implements Action {
    private static ActionVoid obj;

    public static ActionVoid getAction() {
        if (obj == null) obj = new ActionVoid();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        return ;
    }
}
