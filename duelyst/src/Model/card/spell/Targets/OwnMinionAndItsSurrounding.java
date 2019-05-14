package Model.card.spell.Targets;

import Controller.Game;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Target;
import exeption.InvalidCellException;

import java.util.ArrayList;
import java.util.Collections;

public class OwnMinionAndItsSurrounding implements Target{
    private static OwnMinionAndItsSurrounding obj;

    public static OwnMinionAndItsSurrounding getTargetInstance() {
        if (obj == null) {
            obj = new OwnMinionAndItsSurrounding();
        }
        return obj;
    }

    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        if (cell.getCardOnCell()== null || !cell.getCardOnCell().getSuperCollection().getOwner().equals(Game.battle.getPlayer())){
            throw new InvalidCellException();
        }
        ArrayList<Cell> cells = new ArrayList<>();
        Collections.addAll(cells ,  TargetSurroundings.getTargetInstance().getTarget(cell) ) ;
        for (Cell cel : cells){
            if (! (cel.getCardOnCell().getSuperCollection().getOwner().equals(Game.battle.getPlayer())
                    && cell.getCardOnCell().getClass().equals(Hermione.class))){
                cells.remove(cel) ;
            }
        }
        Cell[] cells2 = new Cell[cells.size()];
        cells.toArray(cells2);
        return cells2 ;
    }
}
