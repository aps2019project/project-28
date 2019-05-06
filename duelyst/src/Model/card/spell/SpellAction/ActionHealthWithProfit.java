package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.* ;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionHealthWithProfit implements Action {
    private static ActionHealthWithProfit obj;

    public static ActionHealthWithProfit getAction() {
        if (obj == null) obj = new ActionHealthWithProfit();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        Buff weaknessBuff = new Buff (1 , false , BuffActionHP.getBuffAction() , 6 ) ;
        Buff hollyBuff = new Buff(3 , true , BuffActionHolly.getBuffAction() , 2) ;
        for (Cell cell : cells ) {
            weaknessBuff.deploy(Game.battle.getPlayer() , cell.getCardOnCell());
            hollyBuff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}
