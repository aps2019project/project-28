package Model.card.hermione;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.SpellAction.ActionDeployPoison;
import Model.card.spell.Targets.TargetRandomEnemy;
import exeption.InvalidCellException;

import java.util.ArrayList;

public class BuffEffectsOnHermione {
    private Hermione card ;
    private boolean hasTheDeathCurse = false ;
    private int holyBuffLevel = 0 ;
    private int unholyBuffLevel = 0 ;
    private int originalAttackPoint ;
    private int changedHealthPointDueToBuff = 0 ;
    private boolean hasTheGiantSnakeEffect = false ;
    private boolean hasThePoisonousDagger = false ;
    private boolean canCounterAttack = true ;
    private boolean canMove = true ;
    private int attackCounter = 0 ;
    private ArrayList<Integer> nextTurnsDamage = new ArrayList<>();

    //special power
    private boolean holyBuffDiverter = false ;


    public BuffEffectsOnHermione(Hermione card) {
        this.card = card;
    }

    public void handleOnNewTurn() {
        if (nextTurnsDamage.size() > 0){
            card.changeHealthPoint(nextTurnsDamage.get(0));
            nextTurnsDamage.remove(0);
        }
    }

    public void handleOnAttack(Hermione enemyCard) throws InvalidCellException {
        if (hasThePoisonousDagger){
             Cell[] cells = TargetRandomEnemy.getTargetInstance().getTarget(card.location) ;
             ActionDeployPoison.getAction().deploy(1 , cells);
             hasThePoisonousDagger = false ;
        }

    }

    public void handleOnDeath(){
        //death curse
        if (hasTheDeathCurse){
            for (int i =1 ; i < 5 ; i++){
                for (Cell cell : Battle.getMenu().getMap().getCellsInDistance(card.getLocation() , i)){
                    if (cell.getCardOnCell() != null &&
                            cell.getCardOnCell().getSuperCollection().getOwner().getPlayer() == Battle.getMenu().getEnemyPlayer())
                        cell.getCardOnCell().changeHealthPoint((-8)*card.getAttackPoint());
                }
            }
        }
    }

    public void handleOnDamaged(Hermione enemyCard , int damagePoint){
        if (!enemyCard.getBuffEffects().isHolyBuffDiverter()) handleHollyBuff(damagePoint);
        card.changeHealthPoint((-1)*unholyBuffLevel);
    }



    private void handleHollyBuff(int damagePoint){
        card.changeHealthPoint(Integer.min(damagePoint , holyBuffLevel));
    }


    public boolean isHasTheDeathCurse() {
        return hasTheDeathCurse;
    }

    public int getHolyBuffLevel() {
        return holyBuffLevel;
    }

    public int getOriginalAttackPoint() {
        return originalAttackPoint;
    }

    public int getChangedHealthPointDueToBuff() {
        return changedHealthPointDueToBuff;
    }

    public void setHasTheDeathCurse(boolean hasTheDeathCurse) {
        this.hasTheDeathCurse = hasTheDeathCurse;
    }

    public void setHolyBuffLevel(int holyBuffLevel) {
        this.holyBuffLevel = holyBuffLevel;
    }

    public void setOriginalAttackPoint(int originalAttackPoint) {
        this.originalAttackPoint = originalAttackPoint;
    }

    public void setChangedHealthPointDueToBuff(int changedHealthPointDueToBuff) {
        this.changedHealthPointDueToBuff = changedHealthPointDueToBuff;
    }

    public void changeBackHealthPoint(){
        this.card.changeHealthPoint(-changedHealthPointDueToBuff);
    }

    public boolean isHasTheGiantSnakeEffect() {
        return hasTheGiantSnakeEffect;
    }

    public void setHasTheGiantSnakeEffect(boolean hasTheGiantSnakeEffect) {
        this.hasTheGiantSnakeEffect = hasTheGiantSnakeEffect;
    }

    public boolean isHasThePoisonousDagger() {
        return hasThePoisonousDagger;
    }

    public void setHasThePoisonousDagger(boolean hasThePoisonousDagger) {
        this.hasThePoisonousDagger = hasThePoisonousDagger;
    }

    public boolean allowsAttack() {
        // TODO: 6/5/19 saE
        /*
        * i assume its obvious that this method checks weather or not buffs and spells and .... (every little magical thing)
        * allows the hermione to attack or not
        * */

        return true;
    }

    public boolean allowsCounterAttack() {
        // TODO: 6/5/19 saE
        /*
         * i assume its obvious that this method checks weather or not buffs and spells and .... (every little magical thing)
         * allows the hermione to counterAttack or not
         * */

        return true;

    }

    public boolean allowsMove() {
        // TODO: 6/5/19 saE
        /*
         * i assume its obvious that this method checks weather or not buffs and spells and .... (every little magical thing)
         * allows the hermione to move or not
         * */

        return true;

    }

    public Hermione getCard() {
        return card;
    }

    public void setCard(Hermione card) {
        this.card = card;
    }

    public boolean canCounterAttack() {
        return canCounterAttack;
    }

    public void setCanCounterAttack(boolean canCounterAttack) {
        this.canCounterAttack = canCounterAttack;
    }

    public boolean canMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public int getAttackCounter() {
        return attackCounter;
    }

    public void addAttackCounter() {
        this.attackCounter++;
    }

    public boolean isHolyBuffDiverter() {
        return holyBuffDiverter;
    }

    public void setHolyBuffDiverter(boolean holyBuffDiverter) {
        this.holyBuffDiverter = holyBuffDiverter;
    }

    public int getUnholyBuffLevel() {
        return unholyBuffLevel;
    }

    public void changeUnholyBuffLevel(int unholyBuffLevel) {
        this.unholyBuffLevel += unholyBuffLevel;
    }

    public ArrayList<Integer> getNextTurnsDamage() {
        return nextTurnsDamage;
    }
}
