package Model.card.spell.Targets;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.hermione.Hero;
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
        for (int i = 0 ; i < cells.size() ; i++){
            Cell cel = cells.get(i);
            if (cel.getCardOnCell() == null || cel.getCardOnCell() instanceof Hero ||
                     Battle.getMenu().getPlayer().getMinionsInGame() == null ||!Battle.getMenu().getPlayer().getMinionsInGame().contains(cel.getCardOnCell()))
                cells.remove(i) ;
                i--;
        }
        Cell[] cells2 = new Cell[cells.size()];
        cells.toArray(cells2);
        return cells2 ;
    }

    public Target getTargetClass() {
        return this.obj ;
    }
}
