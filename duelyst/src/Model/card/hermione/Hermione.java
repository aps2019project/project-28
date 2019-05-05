package Model.card.hermione;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.card.Card;
import Model.card.spell.Buff.Buff;
import Model.card.spell.SpecialPower;
import exeption.CantAttackException;
import exeption.DestinationOutOfreachException;
import exeption.MoveTrunIsOverException;

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
    public static final int MOVE_RANGE = 2;
    protected int actionTurn;//0 move    1 attack
    protected Cell location;
    protected boolean canCounterAttack = true ;
    protected boolean canAttack = true ;
    protected int numberOfFlags;
    protected boolean canMove;


    public Hermione(String name, int price, int manaPoint, int healthPoint, int attackPoint
            , SpecialPower specialPower, AttackType attackType, int range) {
        super(name, price, manaPoint);
        this.healthPoint = healthPoint;
        this.attackPoint = attackPoint;
        this.originalAttackPoint = attackPoint ;
        SpecialPower = specialPower;
        this.attackType = attackType;
        this.range = range;
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

    public void attack(Hermione enemyCard) throws DestinationOutOfreachException, CantAttackException {
        if(!this.canAttack)throw new CantAttackException();
      if(this.attackType.canReach(this,enemyCard)){
            enemyCard.setHealthPoint(enemyCard.healthPoint-this.attackPoint);
            enemyCard.counterAttack(this);
            if(enemyCard.getHealthPoint()<=0){
                enemyCard.die();
            }
            return;
        }
      throw new DestinationOutOfreachException();
    }
    public void counterAttack(Hermione enemyCard){
        if(!this.canCounterAttack)return;
        if(this.attackType.canReach(this,enemyCard)){
            this.setHealthPoint(Integer.min(this.healthPoint+this.attackPoint,enemyCard.getAttackPoint()));
        }
    }

    private boolean canMove(int x, int y) throws MoveTrunIsOverException, DestinationOutOfreachException {
        if(this.actionTurn==1)throw new MoveTrunIsOverException();
        if(Game.battle.getMap().getCell(x,y).isFull())throw new DestinationOutOfreachException();

        // TODO: 5/5/19 if the path is not blocked by enemies
        if(Map.getManhattanDistance(this.location,new Cell(x,y)) <= MOVE_RANGE)return true;

        throw new DestinationOutOfreachException();
    }


    public boolean move (int x, int y) throws MoveTrunIsOverException, DestinationOutOfreachException {
        if(!canMove(x,y))return false;
        Game.battle.getMap().getCell(this.location).clear();

        this.setLocation(Game.battle.getMap().getCell(x,y));

        Game.battle.getMap().getCell(x,y).setCardOnCell(this);
        return true;
    }


    public  abstract boolean applySpecialPower(int x, int y);// TODO: 4/15/19 saE

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
        return MOVE_RANGE;
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