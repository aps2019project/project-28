package Model.card.spell.SpellAction;

import Model.Map.Cell;
import Model.card.spell.Spell;

public class ActionHealthWithProfit implements Action {
    private static ActionHealthWithProfit obj;

    public static ActionHealthWithProfit getAction() {
        if (obj == null) obj = new ActionHealthWithProfit();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        //TODO
    }
}
