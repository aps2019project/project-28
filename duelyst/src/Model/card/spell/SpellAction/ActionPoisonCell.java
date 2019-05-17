package Model.card.spell.SpellAction;

import Model.Map.Cell;
import Model.Map.CellAffects;
import Model.card.spell.Spell;

public class ActionPoisonCell implements Action {
    private static ActionPoisonCell obj;

    public static ActionPoisonCell getAction() {
        if (obj == null) obj = new ActionPoisonCell();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            cell.addCellAffect(CellAffects.poison , spell.getDuration(spell.getIndexOfAction(ActionPoisonCell.getAction())));
        }
    }
}

