package Model.card.spell.SpellAction;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionDispel implements Action {
    private static ActionDispel obj;

    public static ActionDispel getAction() {
        if (obj == null) obj = new ActionDispel();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException {
        for (Cell cell : cells) {
            if(cell.getCardOnCell().getAppliedBuffs()==null){
                return;
            }
            for (Buff buff : cell.getCardOnCell().getAppliedBuffs()) {
                if (buff.isItPositive() ^ buff.getPlayer() == Battle.getMenu().getPlayer()) {
                    buff.destroy();
                }
            }
        }
    }
}

