package Model.card.spell.Buff;

import Model.account.player.Player;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff.BuffActions.BuffActions;
import Model.card.spell.BuffTypes.BuffTypes;
import exeption.BuffHasntBeenDeployedYetException;
import exeption.InvalidCellException;

import java.util.ArrayList;

public class Buff {
    static protected ArrayList<Buff> activeBuffs = new ArrayList<>();
    private int duration;
    private Hermione target ;
    private boolean isPositive;
    private Player player;
    private BuffActions action;
    private int perk ;
    private BuffTypes buffType ;

    public Buff(int duration, boolean isPositive,  BuffActions action) {
        this.action = action;
        this.duration = duration;
        this.isPositive = isPositive;
    }

    public Buff(int duration, boolean isPositive,  BuffActions action, BuffTypes buffType) {
        this.action = action;
        this.duration = duration;
        this.isPositive = isPositive;
        this.buffType = buffType ;
        this.buffType.setBuff(this);
    }

    public Buff(Buff buff){
    }

    public Buff(int duration, boolean isPositive, BuffActions action , int perk) {
        this.action = action;
        this.duration = duration;
        this.isPositive = isPositive;
        this.perk = perk ;
    }

    public void deploy(Player player , Hermione target ) throws InvalidCellException{
        this.player = player;
        this.target = target ;
        System.err.println("asd");
        if (target.getAppliedBuffs() == null) System.err.println("Error ! call Saee ! and tell him that Hermione's applied " +
                "buffs are null again ! - _ -");
        target.getAppliedBuffs().add(this);
        activeBuffs.add(this);
        this.action.affect(this);
    }
    public void affect() throws InvalidCellException , BuffHasntBeenDeployedYetException {
        if (this.player == null || this.target == null || this.action == null)
            throw new BuffHasntBeenDeployedYetException();
        this.action.affect(this);
    }

    public void destroy(){
        this.action.destroy(this) ;
        if(this.buffType.isShouldBeDispelled() || this.duration == 0) {
            activeBuffs.remove(this);
            this.target.getAppliedBuffs().remove(this);
        }
    }

    public void handleBuffBeginningOfTurn() throws InvalidCellException , BuffHasntBeenDeployedYetException{
        this.buffType.handleBeginningOfTheTurn();
    }
    public void handleBuffEndOfTurn() throws InvalidCellException{
        this.buffType.handleEndOfTheTurn();
    }

    public int getPerk() {
        return perk;
    }

    public int getDuration() {
        return duration;
    }

    public BuffActions getAction() {
        return action;
    }

    public static ArrayList<Buff> getActiveBuffs() {
        return activeBuffs;
    }


    public Player getPlayer() {
        return player;
    }

    public void reduceDuration() {
        this.duration -= 1;
    }

    public void reduceDuration(int n) {
        this.duration -= n;
    }

    public boolean isItPositive(){
        return isPositive ;
    }

    public Hermione getTarget() {
        return target;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public BuffTypes getBuffType() {
        return buffType;
    }
}


