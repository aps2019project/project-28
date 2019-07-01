package Model.card.spell.Targets;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.player.Player;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetOwnHeroRow implements Target {
    private static TargetOwnHeroRow obj;

    public static TargetOwnHeroRow getTargetInstance() {
        if (obj == null) {
            obj = new TargetOwnHeroRow();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player player = Battle.getMenu().getPlayer() ;
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        Map map = Battle.getMenu().getMap();
        Cell[] cells = new Cell[Map.WHIDTH];
        int y = cell.getY();
        for (int x = 1; x <= Map.HEIGHT; x++) {
            cells[x - 1] = map.getCell(x, y);
        }
        return cells ;
    }

    public Target getTargetClass() {
        return this.obj ;
    }
}
