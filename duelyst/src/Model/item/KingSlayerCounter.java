package Model.item;

import Model.account.Player;

public class KingSlayerCounter {
    private Player player ;
    private int counter = 0 ;
    private boolean isActive = false ;

    public KingSlayerCounter(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCounter() {
        return counter;
    }

    public boolean isActive() {
        return isActive;
    }

    public void increaseCounter() {
        this.counter ++;
    }

    public void resetCounter() {
        this.counter = 0;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
