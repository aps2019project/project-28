package Model.card.hermione;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.CellAffects;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionPoison;
import Model.card.spell.BuffTypes.BuffTypePassive;
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
    private boolean canAttack = true ;
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

        if (card.getLocation().getCellAffect().contains(CellAffects.fire)){
            card.changeHealthPoint(-2);
        }
        if (card.getLocation().getCellAffect().contains(CellAffects.poison)){
            Buff buff = new Buff(1, false, BuffActionPoison.getBuffAction(), new BuffTypePassive());
            try {
                buff.deploy(Battle.getMenu().getPlayer(), card);
            }catch(InvalidCellException e){
                System.err.println("weird InvalidCellException for poisonCellAffect Line52 BuffEffectsOnHermione");
                e.printStackTrace();
            }
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
        //holycell
        if (card.getLocation().getCellAffect().contains(CellAffects.holy)){
            int a = 0 ;
            for (int i = 0 ; i < card.getLocation().getCellAffect().size() ; i++){
                if (card.getLocation().getCellAffect().get(i) == CellAffects.holy) a++;
            }
            if (a == 0) a = 1 ;
            card.changeHealthPoint(Integer.min(damagePoint , a));
        }
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

    public void changeChangedHealthPointDueToBuff(int changedHealthPointDueToBuff) {
        this.changedHealthPointDueToBuff += changedHealthPointDueToBuff;
    }

    public void changeBackHealthPoint(){
        this.card.changeHealthPoint(-changedHealthPointDueToBuff);
    }


    public void setHasTheGiantSnakeEffect(boolean hasTheGiantSnakeEffect) {
        this.hasTheGiantSnakeEffect = hasTheGiantSnakeEffect;
    }


    public void setHasThePoisonousDagger(boolean hasThePoisonousDagger) {
        this.hasThePoisonousDagger = hasThePoisonousDagger;
    }

    public boolean allowsAttack() {
        return canAttack ;
    }

    public boolean allowsCounterAttack() {
        return canCounterAttack ;
    }

    public boolean allowsMove() {
       return canMove ;

    }

    public Hermione getCard() {
        return card;
    }

    public void setCard(Hermione card) {
        this.card = card;
    }


    public void setCanCounterAttack(boolean canCounterAttack) {
        this.canCounterAttack = canCounterAttack;
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

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
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
