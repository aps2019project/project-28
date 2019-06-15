package Model.card.spell.Targets;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.account.Player;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetRandomEnemy implements Target {
    private static TargetRandomEnemy obj;

    public static TargetRandomEnemy getTargetInstance() {
        if (obj == null) {
            obj = new TargetRandomEnemy();
        }
        return obj ;
    }

    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player player = Battle.getMenu().getEnemyPlayer() ;
        return TargetRandom.getTarget(player);
    }
    public Target getTargetClass() {
        return this.obj ;
    }
}
