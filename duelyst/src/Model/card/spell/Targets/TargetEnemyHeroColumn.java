package Model.card.spell.Targets;

import Controller.Game;
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
        Player enemy = Game.battle.getEnemyPlayer() ;
        if (cell != enemy.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        Map map = Game.battle.getMap();
        Cell[] cells = new Cell[Map.HEIGHT];
        int x = cell.getX();
        for (int y = 1; y <= map.HEIGHT; y++) {
            cells[y - 1] = map.getCell(x, y);
        }
        return cells ;
    }
    public Target getTargetClass() {
        return this.obj ;
    }
}
