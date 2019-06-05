package Model.card.hermione;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.CellAffects;
import Model.Map.Map;
import Model.card.Card;
import Model.card.spell.Buff.Buff;
import Model.card.spell.SpecialPower;
import exeption.*;

import java.util.ArrayList;
import java.util.Base64;

public abstract class Hermione extends Card {
    protected int healthPoint;

    protected int originalHealthPoint;
    protected int attackPoint;
    protected Model.card.spell.SpecialPower SpecialPower;
    protected ArrayList<Buff> appliedBuffs;
    protected AttackType attackType;
    protected int range;
    public static final int MOVE_RANGE = 2;
    protected int actionTurn;//0 move    1 attack  2 do nothing
    protected Cell location;
    protected boolean canCounterAttack = true;


    // TODO: 6/5/19 saE baad az in ke allowsAttack(tu buffAffects) ro zaD in moteghayer va getter o setter esh ro bayad hazv koni
    protected boolean canAttack = true;

    protected int numberOfFlags = 0;
    protected boolean hasFlag = false;
    protected boolean canMove = true;
    protected int attackCounter = 0;
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

        // TODO: 6/5/19 saE ------> get your shit out of my code :D
        this.attackCounter++;


        /*
         * handling buffs and spells that have effects when the hermione is attacking
         * */
        this.buffEffects.handleOnAttack();

        /*
         * attacking
         * */
        enemyCard.changeHealthPoint((-1) * this.attackPoint);

        /*
         * handling buffs and spells that have effects when the hermione is being under attack
         * */
        enemyCard.buffEffects.handleOnDamaged(this.attackPoint);

        // TODO: 6/5/19  saE -----> get your shit out of my code :)
        {//saE's shit soon to be removed
            //ina bayad tu ye tabeE miraft (ehtemalan tu hamun OnDamage
            if (enemyCard.getLocation().getCellAffect().contains(CellAffects.holly))
                enemyCard.changeHealthPoint(1);
        }

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

        enemyCard.buffEffects.handleOnDamaged(this.attackPoint);
    }

    private boolean canMove(int x, int y) throws MoveTrunIsOverException, DestinationOutOfreachException, InvalidCellException, DestinationIsFullException, CardCantBeMovedException {

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

        Battle.getMenu().getMap().getCell(this.location).clear();

        this.setLocation(Battle.getMenu().getMap().getCell(x, y));
        if (Battle.getMenu().getMap().getCell(x, y).hasFlag()) {
            this.numberOfFlags++;
            this.hasFlag = true;
        }


        Battle.getMenu().getMap().getCell(x, y).setCardOnCell(this);
        this.actionTurn = 1;
        return true;
    }


    public abstract boolean applySpecialPower(Cell cell) throws InvalidCellException, InvalidCardException, CantSpecialPowerCooldownException;


    public void spawn(Cell cell) {
        this.canMove = true;
        this.originalHealthPoint = this.healthPoint;
        this.setLocation(cell);
    }

    public void die() {
        Battle.getMenu().getMap().getCell(this.getLocation()).setFull(false);
    }

    public void reverseAP() {
        this.attackPoint = this.buffEffects.getOriginalAttackPoint();
    }

    public void reverseHP() {
        this.healthPoint += this.buffEffects.getChangedHealthPointDueToBuff();
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

    public int getActionTurn() {
        return this.actionTurn;
    }

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

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public int getOriginalAttackPoint() {
        return buffEffects.getOriginalAttackPoint();
    }

    public boolean isCanCounterAttack() {
        return canCounterAttack;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setHollyBuffLevel(int level) {
        this.buffEffects.setHollyBuffLevel(level);
    }

    public int getHollyBuffLevel() {
        return this.buffEffects.getHollyBuffLevel();
    }

    public boolean isHasTheDeathCurse() {
        return buffEffects.isHasTheDeathCurse();
    }

    public void setHasTheDeathCurse(boolean hasTheDeathCurse) {
        this.setHasTheDeathCurse(hasTheDeathCurse);
    }

    public void setLostHealthPointDueToBuff(int lostHealthPointDueToBuff) {
        this.buffEffects.setChangedHealthPointDueToBuff(lostHealthPointDueToBuff);
    }

    public int getOriginalHealthPoint() {
        return originalHealthPoint;
    }

    public boolean hasFlag() {
        return hasFlag;
    }

    public void setOriginalHealthPoint(int originalHealthPoint) {
        this.originalHealthPoint = originalHealthPoint;
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

    public void setCanCounterAttack(boolean canCounterAttack) {
        this.canCounterAttack = canCounterAttack;
    }
}