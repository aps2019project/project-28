package Model.card.spell.Targets;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.spell.Target;
import exeption.InvalidCellException;
import java.util.Random ;

import java.util.ArrayList;

public class TargetRandomEnemyMinionInSurrounding implements Target {
    private static TargetRandomEnemyMinionInSurrounding obj;

    public static TargetRandomEnemyMinionInSurrounding getTargetInstance() {
        if (obj == null) {
            obj =  new TargetRandomEnemyMinionInSurrounding();
        }
        return obj ;
    }

    @Override
    public  Cell[] getTarget(Cell cell) throws InvalidCellException {
        Cell[] cells ;
        try {
            cells = TargetSurroundings.getTargetInstance().getTarget(cell);
        } catch (InvalidCellException e){
            throw e ;
        }
        ArrayList<Hermione> minions = new ArrayList<>() ;
        for (Cell cel : cells){
            if (cel.getCardOnCell() instanceof Minion &&
                    cel.getCardOnCell().getSuperCollection().getOwner().getPlayer().equals(Battle.getMenu().getEnemyPlayer()) ){
                minions.add(cel.getCardOnCell()) ;
            }
        }
        if (minions.isEmpty()) throw new InvalidCellException("there are no minions around your hero !") ;
        cells = new Cell[1] ;
        if (minions.size() == 1) {
            cells[0] = minions.get(0).getLocation() ;
            return cells ;
        }
        Random rand = new Random() ;
        int r = rand.nextInt(minions.size()) ;
        cells[0] = minions.get(r).getLocation() ;
        return cells ;
    }
    public Target getTargetClass() {
        return this.obj ;
    }
}
