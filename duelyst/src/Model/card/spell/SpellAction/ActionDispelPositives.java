package Model.card.spell.SpellAction;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionDispelPositives implements Action {
    private static ActionDispelPositives obj;

    public static ActionDispelPositives getAction() {
        if (obj == null) obj = new ActionDispelPositives();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        for (Cell cell : cells) {
            for (Buff buff : cell.getCardOnCell().getAppliedBuffs()) {
                if (buff.isItPositive() && buff.getPlayer() != Battle.getMenu().getPlayer()) {
                    buff.destroy();
                }
            }
        }
    }
}
