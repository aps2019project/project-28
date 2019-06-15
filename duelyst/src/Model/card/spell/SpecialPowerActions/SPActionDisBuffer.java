package Model.card.spell.SpecialPowerActions;

import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionDisBuffer implements Action {
    private static SPActionDisBuffer obj ;
    public static SPActionDisBuffer getSpecialPower(){
        if (obj == null) obj = new SPActionDisBuffer();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null){
                for (Buff buff :cell.getCardOnCell().getAppliedBuffs()){
                    if (buff.isItPositive())cell.getCardOnCell().getAppliedBuffs().remove(buff);
                }
            }
        }
    }

}
