package Model.card.spell.Targets;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.account.player.Player;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetAllEnemyCards  implements Target {
    private static TargetAllEnemyCards obj;

    public static TargetAllEnemyCards getTargetInstance() {
        if (obj == null) {
            obj =  new TargetAllEnemyCards();
        }
        return obj ;
    }

    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player player = Battle.getMenu().getPlayer() ;
        Player enemy = Battle.getMenu().getEnemyPlayer() ;
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        return TargetAllCards.getTarget(enemy);

    }
    public Target getTargetClass() {
        return this.obj ;
    }
}
