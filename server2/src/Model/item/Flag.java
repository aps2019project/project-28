package Model.item;

import Model.Map.Cell;

public class Flag {
    private Cell location;
    private boolean isCollected = false;

    public Flag(Cell location) {
        this.location = location;
    }

    public Cell getLocation() {
        return location;
    }

    public boolean isCollected() {
        return isCollected;
    }
}
