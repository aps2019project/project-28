package Model.card.hermione;

import Model.Map.Cell;
import Model.card.spell.SpellAction.ActionDeployPoison;
import Model.card.spell.Targets.TargetRandomEnemy;
import exeption.InvalidCellException;

public class BuffEffectsOnHermione {
    private Hermione card ;
    private boolean hasTheDeathCurse = false ;
    private int HollyBuffLevel = 0 ;
    private int originalAttackPoint ;
    private int changedHealthPointDueToBuff = 0 ;
    private boolean hasTheGiantSnakeEffect = false ;
    private boolean hasThePoisonousDagger = false ;

    public BuffEffectsOnHermione(Hermione card) {
        this.card = card;
    }

    public void handle() throws InvalidCellException {
        if (hasThePoisonousDagger){
             Cell[] cells = TargetRandomEnemy.getTargetInstance().getTarget(card.location) ;
             ActionDeployPoison.getAction().deploy(1 , cells);
             hasThePoisonousDagger = false ;
        }
    }



    public boolean isHasTheDeathCurse() {
        return hasTheDeathCurse;
    }

    public int getHollyBuffLevel() {
        return HollyBuffLevel;
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

    public void setHollyBuffLevel(int hollyBuffLevel) {
        HollyBuffLevel = hollyBuffLevel;
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
}
