package Model.card.spell.SpellAction;

import Controller.Game;
import Controller.menu.Battle;
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
            Buff buff = new Buff(spell.getDuration(spell.getIndexOfAction(ActionChangeHP.getAction())), spell.getPerk(spell.getIndexOfAction(ActionChangeHP.getAction())) > 0, BuffActionHP.getBuffAction());
            buff.deploy(Battle.getMenu().getPlayer(), cell.getCardOnCell());
            card.changeHealthPoint(spell.getPerk(spell.getIndexOfAction(ActionChangeHP.getAction())));
        }
    }
}
