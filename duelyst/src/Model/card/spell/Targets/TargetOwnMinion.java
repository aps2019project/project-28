package Model.card.spell.Targets;

import Controller.Game;
import Model.Map.Cell;
import Model.account.Player;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetOwnMinion implements Target {
    private static TargetOwnMinion obj;

    public static TargetOwnMinion getTargetInstance() {
        if (obj == null) {
            obj = new TargetOwnMinion();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player player = Game.battle.getPlayer() ;
        if (!player.getMinionsInGame().contains(cell.getCardOnCell())) {
            throw new InvalidCellException();
        }
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}
