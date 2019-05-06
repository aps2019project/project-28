package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.card.spell.Buff.BuffActions.BuffActionPoison ;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionAllPoison implements Model.card.spell.SpellAction.Action {
    private static ActionAllPoison obj;

    public static ActionAllPoison getAction() {
        if (obj == null) obj = new ActionAllPoison();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            //cell.getCardOnCell().changeHealthPoint(-1);
            Buff poisonBuff = new Buff(4, false, BuffActionPoison.getBuffAction());
            poisonBuff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());

        }
    }
}


