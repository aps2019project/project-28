package Model.card.spell.Targets;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.account.Player;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetOwnHero implements Target {
    private static TargetOwnHero obj;

    public static TargetOwnHero getTargetInstance() {
        if (obj == null) {
            obj = new TargetOwnHero();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player player = Battle.getMenu().getPlayer() ;
        if (cell != player.getDeck().getHero().getLocation()) {
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
