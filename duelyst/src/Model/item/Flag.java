package Model.item;

import Model.Map.Cell;

public class Flag extends Item{

    private Cell location;

    public Flag(String name, String effect, int itemID){
        super(name, effect, itemID);
    }

    public void setLocation(Cell location) {
        this.location = location;
    }

    public Cell getLocation() {
        return location;
    }

    public void deploy(){

    }
}
