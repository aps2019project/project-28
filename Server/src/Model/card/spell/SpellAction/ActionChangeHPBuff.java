package Model.card.spell.SpellAction;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHP;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionChangeHPBuff implements Action {
    private static ActionChangeHPBuff obj;

    public static ActionChangeHPBuff getAction() {
        if (obj == null) obj = new ActionChangeHPBuff();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        for (Cell cell : cells) {
            Hermione card = cell.getCardOnCell();
            Buff buff = new Buff(spell.getDuration(spell.getIndexOfAction(ActionChangeHPBuff.getAction())), spell.getPerk(spell.getIndexOfAction(ActionChangeHPBuff.getAction())) > 0, BuffActionHP.getBuffAction());
            buff.deploy(Battle.getMenu().getPlayer(), cell.getCardOnCell());
            card.changeHealthPoint(spell.getPerk(spell.getIndexOfAction(ActionChangeHPBuff.getAction())));
        }
    }
}
