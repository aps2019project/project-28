package Model.card.spell.SpecialPowerActions;

import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionPersianChamp implements Action {
    private static SPActionPersianChamp obj ;
    public static SPActionPersianChamp getSpecialPower(){
        if (obj == null) obj = new SPActionPersianChamp();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null){
                int c = cell.getCardOnCell().getBuffEffects().getAttackCounter();
                cell.getCardOnCell().changeAttackPoint(5 * c);
            }
        }
    }

    @Override
    public void reverse(Hermione card) {
        int c = card.getBuffEffects().getAttackCounter() - 1;
        card.changeAttackPoint((-5) * c);
    }
}
