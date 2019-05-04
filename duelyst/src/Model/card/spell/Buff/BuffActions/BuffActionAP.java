package Model.card.spell.Buff.BuffActions;

import Model.card.spell.Buff.Buff;

public class BuffActionAP implements Model.card.spell.BuffActions {
    private static BuffActionAP obj ;
    public static BuffActionAP getBuffAction(){
        if (obj==null) obj = new BuffActionAP() ;
        return obj;
    }

    public void affect(Buff buff){
        if (buff.getTarget().getAttackPoint() == buff.getTarget().getOriginalAttackPoint()){
            buff.getTarget().changeAttackPoint(buff.getPerk());
        }
    }
    @Override
    public void destroy(Buff buff){
        buff.getTarget().setAttackPoint(buff.getTarget().getOriginalAttackPoint());
    }

}
