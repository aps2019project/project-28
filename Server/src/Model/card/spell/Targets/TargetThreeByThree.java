package Model.card.spell.Targets;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.Map;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetThreeByThree implements Target {
    private static TargetEnemyCard obj;


    public static TargetEnemyCard getTargetInstance() {
        if (obj == null) {
            obj = new TargetEnemyCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        int x = cell.getX();
        int y = cell.getY();
        Map map = Battle.getMenu().getMap();
        if (x > Map.WHIDTH - 2 || y > Map.HEIGHT - 2) {
            throw new InvalidCellException();
        }
        Cell[] cells = {cell, map.getCell(x, y + 1), map.getCell(x, y + 2), map.getCell(x + 1, y),
                map.getCell(x + 2, y), map.getCell(x + 1, y + 1), map.getCell(x + 1, y + 2),
                map.getCell(x + 2, y + 1), map.getCell(x + 2, y + 2)};

        return cells ;
    }

    public Target getTargetClass() {
        return this.obj ;
    }
}