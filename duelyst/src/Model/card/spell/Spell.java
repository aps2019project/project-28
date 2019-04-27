package Model.card.spell;

import Controller.Battle;
import Controller.Game;
import Controller.Match;
import Model.account.Player;
import Model.card.Card;
import Model.Map.*;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Arrays;


public class Spell extends Card {
    protected Target target;
    protected Action action;
    protected int duration;
    protected int perk;

    public void decreaseDuration() {
        this.duration--;
    }

    public Spell(int cardID, String name, int price, int manaPoint) {
        super(cardID, name, price, manaPoint);
    }

    public void deploy(Player player, Player enemy, Cell cell) {
        this.target.deploy(player, enemy, cell, this);
    }

    public void deployAction(Cell... cells) {
        this.action.deploy(this, cells);
    }
}
