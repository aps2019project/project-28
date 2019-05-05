package Model.card.spell.Targets;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.hermione.Melee;
import Model.card.spell.Target;
import Model.item.ItemTargetEnemyHermione;
import exeption.InvalidCellException;

public class TargetRangedAndHybrid implements Target {
        private static ItemTargetEnemyHermione obj ;
        public static ItemTargetEnemyHermione getTargetClass() {
            if (obj == null) obj = new ItemTargetEnemyHermione() ;
            return obj ;
        }

        @Override
        public Cell[] getTarget(Cell cell) throws InvalidCellException {
            if (cell.getCardOnCell().getAttackType().getClass().equals(Melee.class)){
                throw new InvalidCellException("selected card is not suitable for this item !") ;
            }
            return new Cell[] {cell} ;
        }
    }

