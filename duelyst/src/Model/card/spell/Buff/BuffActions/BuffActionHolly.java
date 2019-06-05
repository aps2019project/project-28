package Model.card.spell.Buff.BuffActions;

import Model.card.spell.Buff.Buff;

public class BuffActionHolly implements BuffActions {
    private static BuffActionHolly obj ;

    public static BuffActionHolly getBuffAction(){
        if (obj==null) obj = new BuffActionHolly() ;
        return obj;
    }

    public void affect(Buff buff){
        buff.getTarget().getBuffEffects().setHolyBuffLevel(buff.getTarget().getBuffEffects().getHolyBuffLevel());
        System.err.println("just did some HollyBuff stuff : " + buff.getPerk() + " on " + buff.getTarget().getName());
    }
    @Override
    public void destroy(Buff buff){
        buff.getTarget().getBuffEffects().setHolyBuffLevel(0);
        System.err.println("destroyed hollyBuff");
    }
}
