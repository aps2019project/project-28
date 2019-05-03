package Model.card.spell;

import Model.Map.Cell;
import Model.account.Player;
import exeption.InvalidCellException;

public class TargetOwnHero implements Target {
    private static TargetEnemyCard obj;

    public static TargetEnemyCard getTargetInstance() {
        if (obj == null) {
            obj = new TargetEnemyCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws InvalidCellException {
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}
