package Model.card.spell.Targets;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetTwoByTwo implements Target {
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
        if (x == Map.WIDTH || y == Map.HEIGHT) {
            throw new InvalidCellException();
        }
        Cell[] cells = {cell, map.getCell(x, y + 1), map.getCell(x + 1, y), map.getCell(x + 1, y + 1)};
        return cells ;
    }
}
