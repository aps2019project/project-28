package Model.card.hermione;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.card.Card;
import Model.card.spell.Buff;
import Model.card.spell.SpecialPower;

import java.util.ArrayList;

public abstract class Hermione extends Card {

    protected int healthPoint;
    protected int attackPoint;
    protected int originalAttackPoint ;
    protected Model.card.spell.SpecialPower SpecialPower;
    private ArrayList<Buff> appliedBuffs ;
    private boolean hasHollyBuff = false ;
    protected AttackType attackType;
    protected int range;
    protected int moveRange;
    protected int actionTurn;//0 move    1 attack
    protected Cell location;
    protected boolean canCounterAttack = true ;
    protected boolean canAttack = true ;
    protected int numberOfFlags;
    protected boolean canMove;


    public Hermione(int cardID, String name, int price, int manaPoint, int healthPoint, int attackPoint
            , SpecialPower specialPower, AttackType attackType, int range, int moveRange) {
        super(cardID, name, price, manaPoint);
        this.healthPoint = healthPoint;
        this.attackPoint = attackPoint;
        this.originalAttackPoint = attackPoint ;
        SpecialPower = specialPower;
        this.attackType = attackType;
        this.range = range;
        this.moveRange = moveRange;
    }

    public void setOriginalAttackPoint(int originalAttackPoint) {
        this.originalAttackPoint = originalAttackPoint;
    }

    public void setActionTurn(int actionTurn) {
        this.actionTurn = actionTurn;
    }

    public void setCanCounterAttack(boolean canCounterAttack) {
        this.canCounterAttack = canCounterAttack;
    }

    public void attack(Cell cell){
        Hermione enemyCard= cell.getCardOnCell();
        if(this.attackType.canReach(this,enemyCard)){
            enemyCard.setHealthPoint(enemyCard.healthPoint-this.attackPoint);
            enemyCard.counterAttack(this);
            if(enemyCard.getHealthPoint()<=0){
                enemyCard.die();
            }
        }
    }
    public void counterAttack(Hermione enemyCard){
        if(this.attackType.canReach(this,enemyCard)){
            this.setHealthPoint(Integer.min(this.healthPoint+this.attackPoint,enemyCard.getAttackPoint()));
        }
    }

    private boolean canMove(int x,int y){
        if(this.actionTurn==1)return false;
        if(Game.battle.getMap().getCell(x,y).isFull())return false;

        if(Map.getManhattanDistance(this.location,new Cell(x,y))<=this.moveRange)return true;
        return false;
    }


    public boolean move (int x, int y){
        if(!canMove(x,y))return false;
        Game.battle.getMap().getCell(this.location).clear();

        this.setLocation(Game.battle.getMap().getCell(x,y));

        Game.battle.getMap().getCell(x,y).setCardOnCell(this);
        return true;
    }


    public  abstract boolean applySpecialPower();// TODO: 4/15/19 saE

    private void handleAppliedBuffs(){
        for (Buff appliedBuff : this.appliedBuffs) {
            appliedBuff.affect();
        }
    }

    public void spawn(Cell cell){
        this.setLocation(cell);
    }
    public void die(){
        Game.battle.getMap().getCell(this.getLocation()).setFull(false);
    }

    public void reverseChanges(){
        this.attackPoint=this.originalAttackPoint;
        // TODO: 4/29/19 age chizi munde
    }




    public void changeHealthPoint(int healthPoint) {
        this.healthPoint += healthPoint;
    }
    public void changeAttackPoint(int attackPoint) {
        this.attackPoint = attackPoint;
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

    public ArrayList<Buff> getAppliedBuffs() {
        return appliedBuffs;
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

    public int getActionTurn(){return this.actionTurn;}
    public Cell getLocation() {
        return location;
    }

    public void setLocation(Cell location) {
        this.location = location;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }

    public boolean CanAttack() {		    
        return canAttack;
    }
    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public int getOriginalAttackPoint() {
        return originalAttackPoint;
    }

    public boolean isCanCounterAttack() {
        return canCounterAttack;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setHasHollyBuff(boolean hasHollyBuff) {
         this.hasHollyBuff = hasHollyBuff;
    }
}