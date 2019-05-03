package Model.card.spell;

import Model.Map.Cell;
import Model.account.Player;
import exeption.InvalidCellException;

public class TargetAllEnemyCards  implements Target {
    private static TargetAllEnemyCards obj;

    public static TargetAllEnemyCards getTargetInstance() {
        if (obj == null) {
            obj =  new TargetAllEnemyCards();
        }
        return obj ;
    }

    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws InvalidCellException {
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        return TargetAllCards.getTarget(enemy);

    }
}
