package Model.card.hermione;

public class BuffEffectsOnHermione {
    private boolean hasTheDeathCurse = false ;
    private int HollyBuffLevel = 0 ;
    private int originalAttackPoint ;
    private int lostHealthPointDueToBuff = 0 ;


    public boolean isHasTheDeathCurse() {
        return hasTheDeathCurse;
    }

    public int getHollyBuffLevel() {
        return HollyBuffLevel;
    }

    public int getOriginalAttackPoint() {
        return originalAttackPoint;
    }

    public int getLostHealthPointDueToBuff() {
        return lostHealthPointDueToBuff;
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

    public void setLostHealthPointDueToBuff(int lostHealthPointDueToBuff) {
        this.lostHealthPointDueToBuff = lostHealthPointDueToBuff;
    }
}
