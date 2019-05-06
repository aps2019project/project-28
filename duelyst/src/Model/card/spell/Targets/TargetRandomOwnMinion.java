package Model.card.spell.Targets;

import Controller.Game;
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
        Player player = Game.battle.getPlayer() ;
        Random rand = new Random() ;
        int r = rand.nextInt(player.getMinionsInGame().size()) ;
        return new Cell[] {player.getMinionsInGame().get(r).getLocation()} ;
    }
}