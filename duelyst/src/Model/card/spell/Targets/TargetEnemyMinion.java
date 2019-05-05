package Model.card.spell.Targets;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetEnemyMinion implements Target {
    private static TargetEnemyMinion obj;

    public static TargetEnemyMinion getTargetInstance() {
        if (obj == null) {
            obj = new TargetEnemyMinion();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws InvalidCellException {
        if (enemy.getMinionsInGame().contains(cell.getCardOnCell())){
            Cell[] cells = new Cell[1] ;
            cells[0] = cell ;
            return cells ;
        }
        else throw new InvalidCellException();
    }
}
