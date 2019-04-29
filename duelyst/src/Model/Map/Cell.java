package Model.Map;

import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff;
import Model.card.spell.Spell;

import java.util.ArrayList;



public class Cell {
    private int x;
    private int y;
    private Hermione cardOnCell;
    private ArrayList<Spell> appliedSpells;
    private ArrayList<Buff> appliedBuffs ;
    private boolean hasFlag = false;
    private boolean isFull = false;
    private CellAffects cellAffect = CellAffects.normal ;

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
    public void applySpellOnCell(Spell spell){
        this.appliedSpells.add(spell) ;
    }

    public void applyAllSpells(){
        for (Spell spell : appliedSpells){
            spell.deployAction(this);
        }
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

    public ArrayList<Buff> getAppliedBuffs() {
        return appliedBuffs;
    }

    public ArrayList<Spell> getAppliedSpells() {
        return appliedSpells;
    }

    public void applyBuffOnCell(Buff buff){
        this.appliedBuffs.add(buff);
    }

    public CellAffects getCellAffect() {
        return cellAffect;
    }

    public void setCellAffect(CellAffects cellAffect) {
        this.cellAffect = cellAffect;
    }

}