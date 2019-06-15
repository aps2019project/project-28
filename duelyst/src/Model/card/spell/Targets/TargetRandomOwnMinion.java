package Model.card.spell.Targets;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.account.Player;
import Model.card.spell.Target;
import exeption.InvalidCellException;

import java.util.Random;

public class TargetRandomOwnMinion implements Target {
    private static TargetRandomOwnMinion obj;
        public static TargetRandomOwnMinion getTargetInstance() {
            if (obj == null) {
                obj = new TargetRandomOwnMinion();
            }
            return obj ;
        }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player player = Battle.getMenu().getPlayer() ;
        Random rand = new Random() ;
        int r = rand.nextInt(player.getMinionsInGame().size()) ;
        return new Cell[] {player.getMinionsInGame().get(r).getLocation()} ;
    }
    public Target getTargetClass() {
        return this.obj ;
    }
}
