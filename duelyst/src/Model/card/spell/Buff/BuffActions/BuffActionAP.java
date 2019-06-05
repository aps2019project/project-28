package Model.card.spell.Buff.BuffActions;

import Model.card.spell.Buff.Buff;

public class BuffActionAP implements BuffActions {
    private static BuffActionAP obj ;
    public static BuffActionAP getBuffAction(){
        if (obj==null) obj = new BuffActionAP() ;
        return obj;
    }

    public void affect(Buff buff){
        if (buff.getTarget().getAttackPoint() == buff.getTarget().getBuffEffects().getOriginalAttackPoint()){
            buff.getTarget().changeAttackPoint(buff.getPerk());
            System.err.println("just changed some ap : " + buff.getPerk() + "on" + buff.getTarget().getName());
        }
    }
    @Override
    public void destroy(Buff buff){
        buff.getTarget().setAttackPoint(buff.getTarget().getBuffEffects().getOriginalAttackPoint());
        System.err.println("destroyed APBuff");
    }

}
