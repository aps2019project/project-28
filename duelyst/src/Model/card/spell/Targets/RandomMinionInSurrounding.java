package Model.card.spell.Targets;

import Controller.Game;
import Model.Map.Cell;
import Model.account.Player;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.card.spell.Target;
import exeption.InvalidCellException;
import java.util.Random ;

import java.util.ArrayList;

public class RandomMinionInSurrounding implements Target {
    private static RandomMinionInSurrounding obj;

    public static RandomMinionInSurrounding getTargetInstance() {
        if (obj == null) {
            obj =  new RandomMinionInSurrounding();
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
            if (cel.getCardOnCell().getClass().equals(Minion.class)){
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
