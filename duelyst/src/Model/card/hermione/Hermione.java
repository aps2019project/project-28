package Model.card.hermione;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.card.Card;
import Model.card.spell.AttackType;
import Model.card.spell.SpecialPower;
import Model.card.spell.Spell;

import java.util.ArrayList;

public class Hermione extends Card {

    protected int healthPoint;
    protected int attackPoint;
    protected Model.card.spell.SpecialPower SpecialPower;
    protected ArrayList<Spell> appliedSpells;
    protected AttackType attackType;
    protected int range;
    protected int moveRange;
    protected boolean canAttack;
    protected boolean canMove;
    protected Cell location;
    protected boolean canCounterattack;
    protected int numberOfFlags;

    public Hermione(int cardID, String name, int price, int manaPoint, int healthPoint, int attackPoint, SpecialPower specialPower, AttackType attackType, int range, int moveRange) {
        super(cardID, name, price, manaPoint);
        this.healthPoint = healthPoint;
        this.attackPoint = attackPoint;
        SpecialPower = specialPower;
        this.attackType = attackType;
        this.range = range;
        this.moveRange = moveRange;
    }

    private boolean canMove(int x,int y){
        if(!this.canMove)return false;

        if(Map.getManhattanDistance(this.location,new Cell(x,y))<=this.moveRange)return true;
        return false;
    }
    
    public boolean move (int x, int y){
        if(!canMove(x,y))return false;
        this.setLocation(Game.battle.getMap().getCell(x,y));
        return true;
    }

    public boolean applySpecialPower(Cell cell){
        // TODO: 4/14/19 saE's job
        return true;
    }
    private void handleAppliedSpells(){
        for (Spell appliedSpell : this.appliedSpells) {
            appliedSpell.deploy(Game.battle.getMe(this.superCollection.getOwner())
                    ,Game.battle.getEnemy(super.superCollection.getOwner()));
            // TODO: 4/15/19 im not sure if its implemented correctly
        }
    }

    private void deploy(Cell cell){
        this.setLocation(cell);
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public void setAttackPoint(int attackPoint) {
        this.attackPoint = attackPoint;
    }

    public SpecialPower getSpecialPower() {
        return SpecialPower;
    }

    public void setSpecialPower(SpecialPower specialPower) {
        SpecialPower = specialPower;
    }

    public ArrayList<Spell> getAppliedSpells() {
        return appliedSpells;
    }

    public void setAppliedSpells(ArrayList<Spell> appliedSpells) {
        this.appliedSpells = appliedSpells;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getMoveRange() {
        return moveRange;
    }

    public void setMoveRange(int moveRange) {
        this.moveRange = moveRange;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public Cell getLocation() {
        return location;
    }

    public void setLocation(Cell location) {
        this.location = location;
    }

    public boolean isCanCounterattack() {
        return canCounterattack;
    }

    public void setCanCounterattack(boolean canCounterattack) {
        this.canCounterattack = canCounterattack;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }
}