package Model.card.spell.SpecialPowerActions;

import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionUnholyer implements Action {
    private static SPActionUnholyer obj ;
    public static SPActionUnholyer getSpecialPower(){
        if (obj == null) obj = new SPActionUnholyer();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null){
                cell.getCardOnCell().getBuffEffects().changeUnholyBuffLevel(1);
            }
        }
    }

}
