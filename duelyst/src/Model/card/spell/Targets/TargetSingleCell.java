package Model.card.spell.Targets;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetSingleCell implements Target {
    private static TargetSingleCell obj;

    public static TargetSingleCell getTargetInstance() {
        if (obj == null) {
            obj =  new TargetSingleCell();
        }
        return obj ;
    }

    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        if (cell.getCardOnCell() == null) {
            throw new InvalidCellException();
        }
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}
