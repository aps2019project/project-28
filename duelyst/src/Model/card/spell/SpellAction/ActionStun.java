package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionStun;
import Model.card.spell.Spell;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

public class ActionStun implements Action {
    private static ActionStun obj;

    public static ActionStun getAction() {
        if (obj == null) obj = new ActionStun();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCardException, InvalidCellException {
        for (Cell cell : cells) {
            Buff buff = new Buff(spell.getDuration(), false, BuffActionStun.getBuffAction());
            System.err.println(cell.getCardOnCell().getName());
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}
