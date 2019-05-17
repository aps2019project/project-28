package Model.card.spell;

import Model.account.Player;
import Model.card.Card;
import Model.Map.*;
import Model.card.spell.SpellAction.Action;
import Model.card.spell.SpellAction.ActionVoid;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

import java.util.ArrayList;
import java.util.Collections;


public class Spell extends Card {

    protected ArrayList<Spell> activeSpells = new ArrayList<>() ;
    protected Target target;
    protected Cell[] targetCell= new Cell[Map.WIDTH*Map.HEIGHT];
    protected ArrayList<Action> actions = new ArrayList<>();
    protected int duration;
    protected int perk;
    protected ArrayList<Integer> perks = new ArrayList<>();
    protected ArrayList<Integer> durations = new ArrayList<>();

    public void decreaseDuration() {
        this.duration--;
    }

    public Spell(String name, int price , int manaPoint, int duration , int perk , String info, Target target, Action... actions ) {
        super( name, price, manaPoint, info);
        this.durations.add(duration) ;
        this.perks.add(perk) ;
        Collections.addAll(this.actions, actions) ;
        this.target = target;
    }

    public void addAction(Action action , int perk , int duration){
        this.actions.add(action) ;
        this.perks.add(perk);
        this.durations.add(duration);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Target getTarget() {
        return target;
    }

    public Target getTargetClass() {
        return this.target.getTargetClass();
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public int getDuration(int i) {
        if (i < durations.size() )
            return durations.get(i);
        else return 0 ;
    }

    public int getPerk(int i) {
        if (i < perks.size())
            return perks.get(i);
        else return 0 ;
    }

    public Action getAction(int i){
        if (i < actions.size())
            return actions.get(i);
        else return ActionVoid.getAction() ;
    }

    public int getIndexOfAction(Action action){
        return actions.indexOf(action) ;
    }



    public void deploy(Player player, Player enemy, Cell cell) throws InvalidCellException, InvalidCardException {
        try{
            activeSpells.add(this);
            if(targetCell == null || targetCell.length == 0||this.target==null) {
                try {
                    targetCell = this.target.getTarget(cell);
                }catch (NullPointerException e){
                    throw new InvalidCellException();
                }
            }
            for (Action action : actions){
                try {
                    action.deploy(this, targetCell);
                }catch (NullPointerException e){
                    System.err.println("it was deployed ! but it didn't do anything ! i hope that's cool ! " + this.getName() + " " + this.getCardID());
                }
            }
            this.duration--;
            if (this.duration == 0) activeSpells.remove(this);
        } catch(Exception e ){
            activeSpells.remove(this) ;
            throw e ;
        }
    }

    public void deployAction(Cell... cells) throws InvalidCellException, InvalidCardException {
        if (cells == null ) return ;
        for (Action action : this.actions)
            action.deploy(this, cells);
    }

}
