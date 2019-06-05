package Model.card.spell.SpecialPowerActions;

import Model.Map.Cell;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionHolyBuffDiverter implements Action {
    private static SPActionHolyBuffDiverter obj ;
    public static SPActionHolyBuffDiverter getSpecialPower(){
        if (obj == null) obj = new SPActionHolyBuffDiverter();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null){

            }
        }
    }
}
