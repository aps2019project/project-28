package Model.card.spell.SpecialPowerActions;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionStun;
import Model.card.spell.BuffTypes.BuffTypePassive;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionStunWhenAttacking implements Action {
    private static SPActionStunWhenAttacking obj ;
    public static SPActionStunWhenAttacking getSpecialPower(){
        if (obj == null) obj = new SPActionStunWhenAttacking();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null){
                cell.getCardOnCell().getBuffEffects().setStunnerInAttack(true);
            }
        }
    }

}
