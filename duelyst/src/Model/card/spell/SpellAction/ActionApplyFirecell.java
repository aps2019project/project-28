package Model.card.spell.SpellAction;

import Model.Map.Cell;
import Model.Map.CellAffects;
import Model.card.spell.Spell;

public class ActionApplyFirecell implements Action {
    private static ActionApplyFirecell obj;

    public static ActionApplyFirecell getAction() {
        if (obj == null) obj = new ActionApplyFirecell();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            cell.addCellAffect(CellAffects.fire);
        }
    }
}
