package Model.card.spell.Targets;

import Model.Map.Cell;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetAllOwnMinions implements Target {
    public static Target getTargetClass() {
        return null;
    }

    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        return new Cell[0];
    }
    //TODO saee

}
