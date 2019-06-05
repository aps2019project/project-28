package Model.card.spell.Targets;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetEnemyHeroColumn implements Target {
    private static TargetEnemyHeroColumn obj;

    public static TargetEnemyHeroColumn getTargetInstance() {
        if (obj == null) {
            obj = new TargetEnemyHeroColumn();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player enemy = Battle.getMenu().getEnemyPlayer() ;
        if (cell != enemy.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        Map map = Battle.getMenu().getMap();
        Cell[] cells = new Cell[Map.BALA_PAEEN_Y];
        int x = cell.getX();
        for (int y = 1; y <= map.BALA_PAEEN_Y; y++) {
            cells[y - 1] = map.getCell(x, y);
        }
        return cells ;
    }
    public Target getTargetClass() {
        return this.obj ;
    }
}
