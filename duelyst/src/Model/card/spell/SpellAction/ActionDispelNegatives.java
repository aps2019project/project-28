package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Spell;

public class ActionDispelNegatives implements Action {
    private static ActionDispelNegatives obj;

    public static ActionDispelNegatives getAction() {
        if (obj == null) obj = new ActionDispelNegatives();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            for (Buff buff : cell.getCardOnCell().getAppliedBuffs()) {
                if (!buff.isItPositive() && buff.getPlayer() == Game.battle.getPlayer()) {
                    buff.destroy();
                }
            }
        }
    }
}
