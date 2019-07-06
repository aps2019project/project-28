package Model.card.spell.SpecialPowerActions;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHP;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionHPBuff implements Action {
    private static SPActionHPBuff obj ;
    public static SPActionHPBuff getSpecialPower(){
        if (obj == null) obj = new SPActionHPBuff();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null){
                Buff buff = new Buff(1 , true , BuffActionHP.getBuffAction() , spell.getPerk(spell.getIndexOfAction(this)));
                buff.deploy(Battle.getMenu().getPlayer() , cell.getCardOnCell());
            }
        }
    }
}
