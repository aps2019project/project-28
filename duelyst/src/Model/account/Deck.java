package Model.account;

import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.item.Usable;

import java.util.ArrayList;

public class Deck {
    private Hero hero ;
    private ArrayList<Minion> minions ;
    private ArrayList<Usable> usables ;

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Minion> getMinions() {
        return minions;
    }

    public ArrayList<Usable> getCollectables() {
        return usables;
    }
}
