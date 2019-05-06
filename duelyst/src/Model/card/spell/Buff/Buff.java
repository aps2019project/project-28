package Model.card.spell.Buff;

import Model.account.Player;
import Model.card.hermione.Hermione;
import exeption.InvalidCellException;

import java.util.ArrayList;

public class Buff {
    static protected ArrayList<Buff> activeBuffs = new ArrayList<>();
    private int duration;
    private Hermione target;
    private boolean isPositive;
    private Player player;
    private Model.card.spell.BuffActions action;
    private int perk;

    public Buff(int duration, boolean isPositive, Model.card.spell.BuffActions action) {
        this.action = action;
        this.duration = duration;
        this.isPositive = isPositive;
    }

    public Buff(Buff buff) {

    }

    public Buff(int duration, boolean isPositive, Model.card.spell.BuffActions action, int perk) {
        this.action = action;
        this.duration = duration;
        this.isPositive = isPositive;
        this.perk = perk;
    }

    public void deploy(Player player, Hermione target) throws InvalidCellException {
        this.player = player;
        this.target = target;
        target.getAppliedBuffs().add(this);
        activeBuffs.add(this);
        this.action.affect(this);
    }

    public void affect() throws InvalidCellException {
        if (this.player == null || this.target == null || this.action == null) return;
        this.action.affect(this);
    }

    public void destroy() {
        this.action.destroy(this);
        activeBuffs.remove(this);
        this.target.getAppliedBuffs().remove(this);
    }

    public void nextTurnForBuffs() {
        for (Buff buff : activeBuffs) {
            buff.duration--;
            if (buff.duration == 0) buff.destroy();
        }
    }

    public int getPerk() {
        return perk;
    }

    public int getDuration() {
        return duration;
    }

    public Model.card.spell.BuffActions getAction() {
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

    public boolean isItPositive() {
        return isPositive;
    }

    public Hermione getTarget() {
        return target;
    }

    public boolean isPositive() {
        return isPositive;
    }
}


