package Model.account;

import Model.card.hermione.Minion;
import Model.item.Collectable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Player {
    private Account user;
    private Hand hand;
    private int maxMana =2;
    private int mana;
    private ArrayList<Minion> minionsInGame ;//this players minions
    private ArrayList<Collectable> collectables;
    private Deck deck;


    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void handleWin(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return player.user.equals(this.user);
    }

    public Account getUser() {
        return user;
    }


    public Hand getHand() {
        return hand;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public ArrayList<Collectable> getCollectables() {
        return collectables;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Minion> getMinionsInGame() {
        return Collections.unmodifiableList(minionsInGame) ;
    }

    public List<Collectable> getUsabless() {
        return Collections.unmodifiableList(collectables);
    }
}
