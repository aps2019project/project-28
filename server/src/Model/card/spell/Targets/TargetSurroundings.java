package Model.card.spell.Targets;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.Map;
import Model.card.spell.Target;
import exeption.InvalidCellException;

import java.util.ArrayList;

public class TargetSurroundings implements Target {
    private static TargetSurroundings obj;

    public static TargetSurroundings getTargetInstance() {
        if (obj == null) {
            obj = new TargetSurroundings();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Map map = Battle.getMenu().getMap();
        int x = cell.getX();
        int y = cell.getY();
        Cell[] cells = {map.getCell(x - 1, y - 1), map.getCell(x, y - 1), map.getCell(x + 1, y - 1),
                map.getCell(x - 1, y), map.getCell(x + 1, y), map.getCell(x - 1, y + 1),
                map.getCell(x, y + 1), map.getCell(x + 1, y + 1)};

        ArrayList<Cell> cells2 = new ArrayList<>() ;
        for (Cell cel : cells){
            if (cel.getCardOnCell() != null ){
                cells2.add(cel) ;
            }
        }
        cells = new Cell[cells2.size()] ;
        int index = 0 ;
        for (Cell cel : cells2){
            cells[index] = cel ;
            index++ ;
        }
        return cells ;
    }

    public Target getTargetClass() {
        return this.obj ;
    }
}
