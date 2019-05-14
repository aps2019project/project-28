package Model.card.spell.Targets;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Target;
import exeption.InvalidCellException;

import java.util.ArrayList;
import java.util.Collections;

public class TargetAllMinionsInSurrounding implements Target {

    private static TargetAllMinionsInSurrounding obj;

    public static TargetAllMinionsInSurrounding getTargetInstance() {
        if (obj == null) {
            obj =  new TargetAllMinionsInSurrounding();
        }
        return obj ;
    }

    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        ArrayList<Cell> cells = new ArrayList<Cell>() ;
        Collections.addAll(cells , TargetSurroundings.getTargetInstance().getTarget(cell)) ;
        for (Cell cel : cells){
            if (!cel.getCardOnCell().getSuperCollection().getOwner().equals(Game.battle.getPlayer()))
                cells.remove(cel) ;
        }
        Cell[] cells2 = new Cell[cells.size()];
        cells.toArray(cells2);
        return cells2 ;
    }
}
