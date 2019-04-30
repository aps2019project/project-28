package Model.card.spell;

import Model.account.Player;
import Model.card.Card;
import Model.Map.*;
import exeption.InvalidCellException;

import java.util.ArrayList;


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

    public Spell(int cardID, String name, int price, int manaPoint) {
        super(cardID, name, price, manaPoint);
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

    public void deploy(Player player, Player enemy, Cell cell) throws Exception {
        try {
            activeSpells.add(this);
            if(targetCells.length == 0) targetCells = this.target.getTarget(player, enemy, cell, this);
            for (Action action : actions){
                action.deploy(this , targetCells);
            }
            this.duration--;
            if (this.duration == 0) activeSpells.remove(this);
        } catch(InvalidCellException e){
            throw e ;
        }
    }

    public void deployAction(Cell... cells) {
        for (Action action : this.actions)
            action.deploy(this, cells);
    }

    public void reverseChanges(Player player , Player enemy , Cell cell){

    }
}
