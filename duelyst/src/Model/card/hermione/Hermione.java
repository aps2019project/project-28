package Model.card.hermione;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.CellAffects;
import Model.Map.Map;
import Model.card.Card;
import Model.card.spell.Buff.Buff;
import Model.card.spell.SpecialPower;
import exeption.*;

import java.util.ArrayList;

public abstract class Hermione extends Card {

    protected int healthPoint;
    protected int originalHealthPoint;
    protected int attackPoint;
    protected Model.card.spell.SpecialPower SpecialPower;
    protected ArrayList<Buff> appliedBuffs;
    protected AttackType attackType;
    protected int range;
    protected boolean comboer = false ;
    public static final int MOVE_RANGE = 2;
    protected int actionTurn;//0 move    1 attack  2 do nothing
    protected Cell location;

    protected int numberOfFlags = 0;
    protected boolean hasFlag = false;
    protected BuffEffectsOnHermione buffEffects = new BuffEffectsOnHermione(this);

    public Hermione(String name, int price, int manaPoint, int healthPoint, int attackPoint
            , SpecialPower specialPower, AttackType attackType, int range, String info) {
        super(name, price, manaPoint, info);
        this.healthPoint = healthPoint;
        this.attackPoint = attackPoint;
        this.buffEffects.setOriginalAttackPoint(attackPoint);
        SpecialPower = specialPower;
        this.attackType = attackType;
        this.range = range;
        this.originalHealthPoint = this.healthPoint;
        this.appliedBuffs = new ArrayList<>();
    }


    public boolean canAttack(Hermione target) throws DestinationOutOfreachException, CantAttackException {
        if (!this.attackType.canReach(this, target)) throw new DestinationOutOfreachException();
        if (this.actionTurn == 2) throw new CantAttackException();
        if (!this.buffEffects.allowsAttack()) throw new CantAttackException();
        return true;
    }

    public void attack(Hermione enemyCard) throws DestinationOutOfreachException, CantAttackException, InvalidCellException {

        //DestinationOutOfReachException is not being thrown anymore
        if (!this.canAttack(enemyCard)) throw new CantAttackException();


        this.getBuffEffects().addAttackCounter();


        /*
         * handling buffs and spells that have effects when the hermione is attacking
         * */
        this.buffEffects.handleOnAttack(enemyCard);

        /*
         * attacking
         * */
        enemyCard.changeHealthPoint((-1) * this.attackPoint);

        /*
         * handling buffs and spells that have effects when the hermione is being under attack
         * */
        enemyCard.buffEffects.handleOnDamaged(this , this.attackPoint);


        if (enemyCard.getHealthPoint() <= 0)
            enemyCard.die();
        else
            enemyCard.counterAttack(this);

        /*
         * stop hermione from attacking again in the same turn
         * */
        this.actionTurn = 2;
    }


    private boolean canCounterAttack(Hermione enemyCard) {
        return
                this.attackType.canReach(this, enemyCard)
                        && this.buffEffects.allowsCounterAttack();
    }

    public void counterAttack(Hermione enemyCard) {

        if (!this.canCounterAttack(enemyCard)) return;

        if (this.healthPoint > 0)
            enemyCard.changeHealthPoint((-1) * this.attackPoint);

        enemyCard.buffEffects.handleOnDamaged(this , this.attackPoint);
    }

    public boolean canMove(int x, int y) throws MoveTrunIsOverException, DestinationOutOfreachException, InvalidCellException, DestinationIsFullException, CardCantBeMovedException {

        if (this.actionTurn != 0) throw new MoveTrunIsOverException();

        if (Battle.getMenu().getMap().getCell(x, y).isFull()) throw new DestinationIsFullException();

        if (Map.getManhattanDistance(this.location, new Cell(x, y)) > MOVE_RANGE)
            throw new DestinationOutOfreachException();
        // TODO: 5/5/19 if the path is not blocked by enemies

        if (!this.buffEffects.allowsMove()) throw new CardCantBeMovedException();

        return true;
    }
    public boolean move(int x, int y) throws MoveTrunIsOverException, DestinationOutOfreachException, InvalidCellException, CardCantBeMovedException, DestinationIsFullException {
        if (!canMove(x, y)) return false;

        this.setLocation(Battle.getMenu().getMap().getCell(x, y));

        this.actionTurn = 1;
        return true;
    }


    public abstract void applySpecialPower(Cell cell) throws InvalidCellException, InvalidCardException, CantSpecialPowerCooldownException;


    public void spawn(Cell cell) {
        this.getBuffEffects().setCanMove(true);

        this.setLocation(cell);
    }

    public void die() {
        try {
            buffEffects.handleOnDeath();
            Battle.getMenu().kill(this);
        } catch (InvalidCardException ignored) {}
    }


    public void changeHealthPoint(int healthPoint) {
        this.healthPoint += healthPoint;
        if (this.healthPoint <= 0) this.healthPoint = 0;
        if (this.healthPoint >= this.originalHealthPoint) this.healthPoint = this.originalHealthPoint;
    }

    public void changeAttackPoint(int attackPoint) {
        this.attackPoint += attackPoint;
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


    public ArrayList<Buff> getAppliedBuffs() {
        return appliedBuffs;
    }

    public AttackType getAttackType() {
        return attackType;
    }


    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Cell getLocation() {
        return location;
    }

    public void setLocation(Cell location) {
        this.location = location;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }


    public int getOriginalHealthPoint() {
        return originalHealthPoint;
    }

    public boolean hasFlag() {
        return hasFlag;
    }


    public BuffEffectsOnHermione getBuffEffects() {
        return buffEffects;
    }

    public void makeNewListForAppliedBuffs() {
        this.appliedBuffs = new ArrayList<>();
    }

    public void setFlag(boolean flag) {
        this.hasFlag = flag;
    }

    public void setActionTurn(int actionTurn) {
        this.actionTurn = actionTurn;
    }


    public boolean isComboer() {
        return comboer;
    }

    public void setComboer(boolean comboer) {
        this.comboer = comboer;
    }

}