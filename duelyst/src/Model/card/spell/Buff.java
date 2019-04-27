package Model.card.spell;

import Model.Map.Cell;
import Model.account.Player;

import java.util.ArrayList;

public class Buff {
    static protected ArrayList<Buff> activeBuffs = new ArrayList<>();
    private int duration;
    private Cell cell ;
    private boolean isPositive;
    private Player player;
    private Action action;

    public Buff(int duration, boolean isPositive, Player player, Action action , Cell cell) {
        this.action = action;
        this.duration = duration;
        this.isPositive = isPositive;
        this.player = player;
        this.cell = cell ;
    }

    public int getDuration() {
        return duration;
    }

    public Action getAction() {
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

    public void destroy(){
        activeBuffs.remove(this);
        this.cell.getAppliedBuffs().remove(this) ;
    }

}


