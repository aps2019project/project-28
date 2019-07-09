package Model.card.spell.SpecialPowerActions;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionPoison;
import Model.card.spell.BuffTypes.BuffTypePassive;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import Model.card.spell.SpellAction.ActionDisarm;
import Model.card.spell.Targets.TargetEnemyCard;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class SPActionTooranianSpy implements Action {
    private static SPActionTooranianSpy obj ;
    public static SPActionTooranianSpy getSpecialPower(){
        if (obj == null) obj = new SPActionTooranianSpy();
        return obj ;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException {
        for (Cell cell : cells){
            if (cell.getCardOnCell() != null){
                Spell disarm = new Spell ("dm" , 0 , 0 , 1 , 1 , "" ,
                        TargetEnemyCard.getTargetInstance() , ActionDisarm.getAction()) ;
                disarm.deploy(Battle.getMenu().getPlayer() , Battle.getMenu().getEnemyPlayer() , cell);
                Buff poison = new Buff(4 , false , BuffActionPoison.getBuffAction() , BuffTypePassive.getBuffTypeInstance());
                poison.deploy(Battle.getMenu().getPlayer() , cell.getCardOnCell());
            }
        }
    }
}
