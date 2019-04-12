package Model.Map;

import Model.card.Card;
import Model.card.spell.Spell;

import java.util.ArrayList;

public class Cell {
    private int x;
    private int y;
    private Card cardOnCell;
    private ArrayList<Spell> appliedSpells;
    private boolean hasFlag = false;
    private boolean isFull = false;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Card getCardOnCell() {
        return cardOnCell;
    }

    public void setCardOnCell(Card cardOnCell) {
        this.cardOnCell = cardOnCell;
    }

    public boolean isHasFlag() {
        return hasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }
}
