package Model.card.spell.SpellAction;

import Model.Map.Cell;
import Model.Map.CellAffects;
import Model.card.spell.Spell;

public class ActionHollyCell implements Action {
    private static ActionHollyCell obj;

    public static ActionHollyCell getAction() {
        if (obj == null) obj = new ActionHollyCell();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            cell.setCellAffect(CellAffects.holly);
        }
    }
}
