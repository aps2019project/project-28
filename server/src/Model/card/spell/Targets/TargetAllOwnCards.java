package Model.card.spell.Targets;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.account.player.Player;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetAllOwnCards implements Target {
    private static TargetAllOwnCards obj;

    public static TargetAllOwnCards getTargetInstance() {
        if (obj == null) {
            obj =  new TargetAllOwnCards();
        }
        return obj ;
    }

    @Override
    public  Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player player = Battle.getMenu().getPlayer() ;
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        return TargetAllCards.getTarget(player);
    }
    public Target getTargetClass() {
        return this.obj ;
    }
}
