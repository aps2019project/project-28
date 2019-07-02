package Model.card.spell.SpecialPowerActions;

import Model.Map.Cell;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionAP implements Action {
    private static SPActionAP obj;

    public static SPActionAP getSpecialPower() {
        if (obj == null) obj = new SPActionAP();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells) {
            if (cell.getCardOnCell() != null) {
                cell.getCardOnCell().changeHealthPoint(spell.getPerk(spell.getIndexOfAction(this)));
            }
        }
    }
}
