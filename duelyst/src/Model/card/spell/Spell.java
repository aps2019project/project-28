package Model.card.spell;

import Model.account.Player;
import Model.card.Card;
import Model.Map.*;

import java.util.ArrayList;


public class Spell extends Card {
    protected ArrayList<Spell> activeSpells = new ArrayList<>() ;
    protected Target target;
    protected ArrayList<Action> actions = new ArrayList<>();
    protected int duration;
    protected int perk;

    public void decreaseDuration() {
        this.duration--;
    }

    public Spell(int cardID, String name, int price, int manaPoint) {
        super(cardID, name, price, manaPoint);
    }

    public void deploy(Player player, Player enemy, Cell cell) {
        activeSpells.add(this);
        this.target.getTarget(player, enemy, cell, this);
        this.duration--;
        if (this.duration == 0) activeSpells.remove(this);
    }

    public void deployAction(Cell... cells) {
        for (Action action : this.actions)
            action.deploy(this, cells);
    }

    public void reverseChanges(Player player , Player enemy , Cell cell){

    }
}
