package Model.card.spell.Targets;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.account.Player;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetOwnCard implements Target {
    private static TargetOwnCard obj;

    public static TargetOwnCard getTargetInstance() {
        if (obj == null) {
            obj = new TargetOwnCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player player = Battle.getMenu().getPlayer() ;
        if (player.getDeck().getHero().getLocation() != cell && !player.getMinionsInGame().contains(cell.getCardOnCell())) {
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
