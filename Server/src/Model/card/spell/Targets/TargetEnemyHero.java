package Model.card.spell.Targets;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.account.player.Player;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetEnemyHero implements Target {
    private static TargetEnemyHero obj;

    public static TargetEnemyHero getTargetInstance() {
        if (obj == null) {
            obj = new TargetEnemyHero();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player enemy = Battle.getMenu().getEnemyPlayer() ;
        if (cell.getCardOnCell() == enemy.getDeck().getHero()) {
            throw new InvalidCellException();
        }
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
    public Target getTargetClass() {
        return this.obj ;
    }
}
