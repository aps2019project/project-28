package Model.card.spell.Targets;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.spell.Spell;
import Model.card.spell.Target;

public class TargetSingleCell implements Target {
    private static TargetSingleCell obj;

    public static TargetSingleCell getTargetInstance() {
        if (obj == null) {
            obj =  new TargetSingleCell();
        }
        return obj ;
    }

    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) {
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}
