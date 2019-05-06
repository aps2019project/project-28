package Model.card.spell.Targets;

import Model.Map.Cell;
import Model.card.hermione.Melee;
import Model.card.spell.Target;
import exeption.InvalidCellException;

public class TargetMelee implements Target {
        private static TargetMelee obj ;
        public static TargetMelee getTargetClass() {
            if (obj == null) obj = new TargetMelee() ;
            return obj ;
        }

        @Override
        public Cell[] getTarget(Cell cell) throws InvalidCellException {
            if (!cell.getCardOnCell().getAttackType().getClass().equals(Melee.class)){
                throw new InvalidCellException("selected card is not suitable for this item !") ;
            }
            return new Cell[] {cell} ;
        }
    }

