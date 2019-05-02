package Model.card.spell;

import Model.Map.Cell;
import Model.account.Player;

public class TargetSingleCell implements Target {
    TargetSingleCell obj;

    public TargetSingleCell getTargetInstance() {
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
