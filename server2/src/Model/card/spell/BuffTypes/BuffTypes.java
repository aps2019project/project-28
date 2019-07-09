package Model.card.spell.BuffTypes;

import Model.card.spell.Buff.Buff;
import exeption.BuffHasntBeenDeployedYetException;
import exeption.InvalidCellException;

import java.util.ArrayList;

public abstract class BuffTypes {
    protected Buff buff ;
    protected static ArrayList<Buff> buffs = new ArrayList<>() ;
    protected boolean shouldBeDispelled = true ;

    public void setBuff(Buff buff) {
        this.buff = buff;
    }

    public static ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public boolean isShouldBeDispelled() {
        return shouldBeDispelled;
    }

    public static void addToBuffs(Buff buff){
        buffs.add(buff) ;
    }
    public abstract void handleBeginningOfTheTurn() throws InvalidCellException , BuffHasntBeenDeployedYetException;

    public abstract void handleEndOfTheTurn() throws InvalidCellException;
}

