package Model.account;

import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.item.Collectable;
import Model.item.Usable;

import java.util.*;
import java.util.Collection;

public class Player {
    private Account user;
    private Hand hand;
    private int maxMana =2;
    private int mana;
    private ArrayList<Collectable> collectables;
    private ArrayList<Usable> usables ;
    private Deck deck;
    private Hero hero ;
    private ArrayList<Minion> minionsInGame ;

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

    public Deck getDeck() {
        return deck;
    }

    public Hero getHero() {
        return hero;
    }

    public List<Minion> getMinionsInGame() {
        return Collections.unmodifiableList(minionsInGame) ;
    }

    public Hand getHand() {
        return hand;
    }

    public int getMana() {
        return mana;
    }

    public List<Usable> getUsabless() {
        return Collections.unmodifiableList(usables);
    }
}
