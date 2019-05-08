package Model.card.spell.BuffTypes;

import Model.card.spell.Buff.Buff;
import exeption.BuffHasntBeenDeployedYetException;
import exeption.InvalidCellException;

public class BuffTypeContinuous extends BuffTypes{
    public BuffTypeContinuous() {
        shouldBeDispelled = false ;
    }

    @Override
    public void handleBeginningOfTheTurn() throws InvalidCellException, BuffHasntBeenDeployedYetException {
        if (buff.getDuration()==0) {
            buff.destroy();
            return ;
        }
        buff.reduceDuration();
        buff.affect();
    }

    @Override
    public void handleEndOfTheTurn() throws InvalidCellException{
        buff.destroy();
    }
}
