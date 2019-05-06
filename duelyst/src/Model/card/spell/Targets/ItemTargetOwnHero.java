package Model.card.spell.Targets;

import Model.Map.Cell;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class ItemTargetOwnHero implements Target {

    public static Target getTargetInstance() {
        return null;
    }

    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        return new Cell[0];
    }

    //TODO SAEE
}
