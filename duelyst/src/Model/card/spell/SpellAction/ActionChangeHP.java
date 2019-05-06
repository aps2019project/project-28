package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHP;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionChangeHP implements Action {
    private static ActionChangeHP obj;

    public static ActionChangeHP getAction() {
        if (obj == null) obj = new ActionChangeHP();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        for (Cell cell : cells) {
            Hermione card = cell.getCardOnCell();
            Buff buff = new Buff(spell.getDuration(), spell.getPerk() > 0, BuffActionHP.getBuffAction());
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}
