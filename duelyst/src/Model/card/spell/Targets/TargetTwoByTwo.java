package Model.card.spell;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import exeption.InvalidCellException;

public class TargetTwoByTwo implements Target {
    private static TargetEnemyCard obj;

    public static TargetEnemyCard getTargetInstance() {
        if (obj == null) {
            obj = new TargetEnemyCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws InvalidCellException {
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
