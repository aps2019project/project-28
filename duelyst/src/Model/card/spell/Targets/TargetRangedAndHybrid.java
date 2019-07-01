package Model.card.spell.Targets;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.hermione.Melee;
import Model.card.hermione.Minion;
import Model.card.spell.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TargetRangedAndHybrid implements Target {
        private static TargetRangedAndHybrid obj ;
        public static TargetRangedAndHybrid getTargetInstance() {
            if (obj == null) obj = new TargetRangedAndHybrid();
            return obj;
        }

    @Override
    public Cell[] getTarget(Cell ignored) {
        List<Cell> list = new ArrayList<>();
        for (Cell cell : Battle.getMenu().getMap().getCells()) {
            if (cell.getCardOnCell() != null && cell.getCardOnCell() instanceof Minion &&
                    !(cell.getCardOnCell().getAttackType() instanceof Melee ))
                list.add(cell);
        }
        if (list.size() == 0) return new Cell[] {};
        int i = (new Random()).nextInt(list.size());
        return new Cell[] {list.get(i)} ;
    }
    public Target getTargetClass() {
        return this.obj ;
    }
    }

