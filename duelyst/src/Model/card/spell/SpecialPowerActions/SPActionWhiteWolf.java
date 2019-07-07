package Model.card.spell.SpecialPowerActions;

import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionWhiteWolf implements Action {
    private static SPActionWhiteWolf obj ;
    public static SPActionWhiteWolf getSpecialPower(){
        if (obj == null) obj = new SPActionWhiteWolf();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null){
                cell.getCardOnCell().getBuffEffects().getNextTurnsDamage().add(6);
                cell.getCardOnCell().getBuffEffects().getNextTurnsDamage().add(4);
            }
        }
    }

}
