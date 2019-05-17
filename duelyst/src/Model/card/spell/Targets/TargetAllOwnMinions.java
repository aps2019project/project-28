package Model.card.spell.Targets;

import Controller.Game;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.spell.Target;
import exeption.InvalidCellException;

import java.util.ArrayList;
import java.util.List;

public class TargetAllOwnMinions implements Target {
    private static TargetAllOwnMinions obj;

    public static TargetAllOwnMinions getTargetInstance() {
        if (obj == null) {
            obj =  new TargetAllOwnMinions();
        }
        return obj ;
    }

    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        List<Minion> minions = Game.battle.getPlayer().getMinionsInGame();
        Cell[] cells = new Cell[minions.size()];
        for (int i = 0 ; i < minions.size() ; i++){
            cells[i] = minions.get(i).getLocation();
            System.err.println("target all minions : " + i);
        }
        return cells ;
    }
    public Target getTargetClass() {
        return this.obj ;
    }
}
