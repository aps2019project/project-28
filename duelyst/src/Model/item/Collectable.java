package Model.item;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.hermione.Hermione;

import java.util.ArrayList;

public class Collectable extends Item {

    private static ArrayList<Collectable> collectables = new ArrayList<>();
    private Cell location;

    public Collectable(String name, String effect, int itemID){
        super(name, effect, itemID);
        collectables.add(this);
    }

    public void setLocation(Cell location) {
        this.location = location;
    }

    public Cell getLocation() {
        return location;
    }

    public static ArrayList<Collectable> getCollectables() {
        return collectables;
    }

    public void deploy(){

    }



}

