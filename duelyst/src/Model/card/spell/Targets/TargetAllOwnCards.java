package Model.card.spell.Targets;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.spell.Spell;
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
    public  Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws InvalidCellException {
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        return TargetAllCards.getTarget(player);
    }
}
