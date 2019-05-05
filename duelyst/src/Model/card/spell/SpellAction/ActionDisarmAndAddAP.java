package Model.card.spell.SpellAction;

import Model.Map.Cell;
import Model.card.spell.Spell;

public class ActionDisarmAndAddAP implements Action {
    private static ActionDisarmAndAddAP obj;

    public static ActionDisarmAndAddAP getAction() {
        if (obj == null) obj = new ActionDisarmAndAddAP();
        return obj;
    }

    //has duration
    @Override
    public void deploy(Spell spell, Cell... cells) {
        spell.decreaseDuration();
        ActionDisarm.getAction().deploy(spell, cells);
        ActionChangeAP.getAction().deploy(spell, cells);
    }
}