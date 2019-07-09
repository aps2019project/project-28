package Model.card.spell.SpecialPowerActions;

import Model.Map.Cell;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionNextTurnDamager implements Action {
    private static SPActionNextTurnDamager obj ;
    public static SPActionNextTurnDamager getSpecialPower(){
        if (obj == null) obj = new SPActionNextTurnDamager();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null && cell.getCardOnCell() instanceof Minion){
                cell.getCardOnCell().getBuffEffects().getNextTurnsDamage().add(spell.getPerk(spell.getIndexOfAction(this)));
            }
        }
    }

}
