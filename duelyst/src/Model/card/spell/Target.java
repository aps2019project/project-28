package Model.card.spell;


import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.hermione.Minion;
import Model.card.spell.Targets.TargetAllMinionsInSurrounding;
import exeption.InvalidCellException;

public interface Target {
    Cell[] getTarget(Cell cell) throws InvalidCellException;
    Target getTargetClass() ;

}


