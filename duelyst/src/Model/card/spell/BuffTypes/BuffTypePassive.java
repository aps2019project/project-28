package Model.card.spell.BuffTypes;

import Model.card.spell.Buff.Buff;
import exeption.BuffHasntBeenDeployedYetException;
import exeption.InvalidCellException;

public class BuffTypePassive extends BuffTypes{
    private static BuffTypePassive obj ;
    public static BuffTypePassive getBuffTypeInstance(){
        if (obj == null) obj = new BuffTypePassive();
        return obj;
    }

    public BuffTypePassive() {
        shouldBeDispelled = true ;
    }

    @Override
    public void handleBeginningOfTheTurn() throws InvalidCellException, BuffHasntBeenDeployedYetException {
        if (buff.getDuration() != 0) {
            buff.reduceDuration();
            buff.affect();
        }
    }

    @Override
    public void handleEndOfTheTurn() throws InvalidCellException{
        if (buff.getDuration()==0) buff.destroy();
    }
}
