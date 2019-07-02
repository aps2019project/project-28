package Model.card.spell.SpecialPowerActions;

import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionCombo implements Action {
    private static SPActionCombo obj ;
    public static SPActionCombo getSpecialPower(){
        if (obj == null) obj = new SPActionCombo();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null){
                cell.getCardOnCell().setComboer(true);
            }
        }
    }

    @Override
    public void reverse(Hermione card) {
        card.setComboer(false);
    }
}
