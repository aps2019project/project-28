package Model.card.spell.Targets;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import Model.card.spell.Targets.TargetEnemyCard;
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
        Map map = Game.battle.getMap();
        if (x > Map.WIDTH - 2 || y > Map.HEIGHT - 2) {
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
