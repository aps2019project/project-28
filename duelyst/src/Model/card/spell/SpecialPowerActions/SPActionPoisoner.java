package Model.card.spell.SpecialPowerActions;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionPoison;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionPoisoner implements Action {
    private static SPActionPoisoner obj ;
    public static SPActionPoisoner getSpecialPower(){
        if (obj == null) obj = new SPActionPoisoner();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null){
                Buff buff = new Buff(3 , false , BuffActionPoison.getBuffAction());
                buff.deploy(Battle.getMenu().getPlayer() , cell.getCardOnCell());
            }
        }
    }

}
