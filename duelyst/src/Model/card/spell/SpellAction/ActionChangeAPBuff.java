package Model.card.spell.SpellAction;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionChangeAPBuff implements Action {
    private static ActionChangeAPBuff obj;

    public static ActionChangeAPBuff getAction() {
        if (obj == null) obj = new ActionChangeAPBuff();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        for (Cell cell : cells) {
            if (cell != null && cell.getCardOnCell() != null) {
                try {
                    Hermione card = cell.getCardOnCell();
                    Buff buff = new Buff(spell.getDuration(spell.getIndexOfAction(ActionChangeAPBuff.getAction())), spell.getPerk(spell.getIndexOfAction(ActionChangeAPBuff.getAction())) > 0, BuffActionAP.getBuffAction());
                    if (cell.getCardOnCell() == null) throw new InvalidCellException();
                    buff.deploy(Battle.getMenu().getPlayer(), card);
                } catch (NullPointerException e) {
                    System.out.println();
                    e.printStackTrace();
                    System.out.println();
                }
            }
        }
    }
}
