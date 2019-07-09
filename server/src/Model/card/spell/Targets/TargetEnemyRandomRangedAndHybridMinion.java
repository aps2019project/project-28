package Model.card.spell.Targets;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.hermione.Melee;
import Model.card.hermione.Minion;
import Model.card.spell.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TargetEnemyRandomRangedAndHybridMinion implements Target {
        private static TargetEnemyRandomRangedAndHybridMinion obj ;
        public static TargetEnemyRandomRangedAndHybridMinion getTargetInstance() {
            if (obj == null) obj = new TargetEnemyRandomRangedAndHybridMinion();
            return obj;
        }

    @Override
        public Cell[] getTarget(Cell ignored) {
        List<Cell> list = new ArrayList<>();
        for (Cell cell : Battle.getMenu().getMap().getCells()) {
            if (cell.getCardOnCell() != null && cell.getCardOnCell() instanceof Minion &&
                    !(cell.getCardOnCell().getAttackType() instanceof Melee ) &&
                      cell.getCardOnCell().getSuperCollection().getOwner().getPlayer() == Battle.getMenu().getEnemyPlayer())
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

