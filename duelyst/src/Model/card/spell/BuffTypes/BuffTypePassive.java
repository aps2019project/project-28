package Model.card.spell.BuffTypes;

import Model.card.spell.Buff.Buff;
import exeption.BuffHasntBeenDeployedYetException;
import exeption.InvalidCellException;

public class BuffTypePassive extends BuffTypes{
    private static BuffTypePassive obj ;

    public BuffTypePassive() {
        shouldBeDispelled = false ;
    }

    @Override
    public void handleBeginningOfTheTurn() throws InvalidCellException, BuffHasntBeenDeployedYetException {
    }

    @Override
    public void handleEndOfTheTurn() throws InvalidCellException{
        buff.destroy();
    }
}
