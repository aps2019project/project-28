package Model.card.spell.Targets;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.card.spell.Target;
import exeption.InvalidCellException;

import static java.lang.StrictMath.abs;

public class Targetwithin2ManhattanDistance implements Target {
    private static TargetTwoByTwo obj;

    public static TargetTwoByTwo getTargetInstance() {
        if (obj == null) {
            obj = new TargetTwoByTwo();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        int x = cell.getX();
        int y = cell.getY();
        Map map = Game.battle.getMap();
        Cell[] cells = new Cell[12] ;
        int index = 0 ;
        for (int i = -2 ; i <= 2 ; i++) {
            for (int j = -2 ; abs(j+i) <= 2 ; j++){
                if (j==0 && i == 0) continue;
                if (Map.getManhattanDistance(cell , map.getCell(x+i , y + j)) <= 2 )
                    cells[index] = map.getCell(x+i , y + j) ;
            }
        }
        return cells ;
    }
}
