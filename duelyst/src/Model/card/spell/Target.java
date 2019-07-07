package Model.card.spell;


import Model.Map.Cell;
import exeption.InvalidCellException;

public interface Target {
    Cell[] getTarget(Cell cell) throws InvalidCellException;
    Target getTargetClass() ;

}


