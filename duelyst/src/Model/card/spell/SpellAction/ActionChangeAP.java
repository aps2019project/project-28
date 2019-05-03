package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.card.spell.Spell;

public class ActionChangeAP implements Action {
    private static ActionChangeAP obj;

    public static ActionChangeAP getAction() {
        if (obj == null) obj = new ActionChangeAP();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            Hermione card = cell.getCardOnCell();
            Buff buff = new Buff(spell.getDuration(), spell.getPerk() > 0, BuffActionAP.getBuffAction());
            buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}
