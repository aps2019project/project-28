package Model.card.spell;

import Model.account.Account;
import Model.account.Collection;
import Model.account.Player;
import Model.card.Card;
import Model.Map.*;
import Model.card.spell.SpellAction.Action;
import exeption.InvalidCellException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Spell extends Card {

    protected ArrayList<Spell> activeSpells = new ArrayList<>() ;
    protected Target target;
    protected Cell[] targetCells ;
    protected ArrayList<Action> actions = new ArrayList<>();
    protected int duration;
    protected int perk;

    public void decreaseDuration() {
        this.duration--;
    }

    public Spell(String name, int price , int manaPoint, int duration , int perk , Target target,Action ... actions ) {
        super( name, price, manaPoint);
        this.duration = duration ;
        this.perk = perk ;
        Collections.addAll(this.actions , actions) ;
        this.target = target;
    }

    public ArrayList<Spell> getActiveSpells() {
        return activeSpells;
    }

    public Target getTarget() {
        return target;
    }

    public Cell[] getTargetCells() {
        return targetCells;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public int getDuration() {
        return duration;
    }

    public int getPerk() {
        return perk;
    }

    public void deploy (Cell cell) throws Exception {
        try{
            activeSpells.add(this);
            if(targetCells.length == 0) targetCells = this.target.getTarget(cell);
            for (Action action : actions){
                action.deploy(this , targetCells);
            }
            this.duration--;
            if (this.duration == 0) activeSpells.remove(this);
        } catch(Exception e ){
            activeSpells.remove(this) ;
            throw e ;
        }
    }

    public void deployAction(Cell... cells) {
        for (Action action : this.actions)
            action.deploy(this, cells);
    }
}
