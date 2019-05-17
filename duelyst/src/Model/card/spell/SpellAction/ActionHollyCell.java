package Model.card.spell.SpellAction;

import Model.Map.Cell;
import Model.Map.CellAffects;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionHollyCell implements Action {
    private static ActionHollyCell obj;

    public static ActionHollyCell getAction() {
        if (obj == null) obj = new ActionHollyCell();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        for (Cell cell : cells) {
            cell.addCellAffect(CellAffects.holly , spell.getDuration(spell.getIndexOfAction(ActionHollyCell.getAction())));
        }
    }
}
