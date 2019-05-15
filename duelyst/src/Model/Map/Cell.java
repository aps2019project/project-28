package Model.Map;

import Model.card.hermione.Hermione;
import Model.item.Collectable;
import exeption.CellIsFullException;
import exeption.DeckAlreadyHasAHeroException;

import java.util.ArrayList;


public class Cell {

    private int x;
    private int y;
    private Hermione cardOnCell;
    private boolean hasFlag = false;
    private boolean isFull = false;
    private boolean hasItem = false;
    private ArrayList<CellAffects> cellAffect = new ArrayList<>() ;
    private Collectable collectable;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setItem(Collectable collectable) {
        this.collectable = collectable;
        this.hasItem = true;
    }

    public Hermione getCardOnCell() {
        return cardOnCell;
    }

    public void setCardOnCell(Hermione cardOnCell) {
        this.cardOnCell = cardOnCell;
        this.isFull=true;
    }
    public void clear (){
        this.cardOnCell=null;
        this.isFull=false;
    }

    public boolean hasFlag() {
        return hasFlag;
    }

    public void setFlag(boolean hasFlag) {
        this.hasFlag=hasFlag;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public boolean hasItem() {
        return hasItem;
    }


    public ArrayList<CellAffects> getCellAffect() {
        return cellAffect;
    }

    public void addCellAffect(CellAffects cellAffect) {
        this.cellAffect.add(cellAffect);
    }

    public Collectable getCollectable() {
        return collectable;
    }

    public void clearCollectable() {
        this.collectable = null;
        this.hasItem=false;
    }
}