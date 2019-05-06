package Model.card.spell.Buff.BuffActions;

import Model.card.spell.Buff.Buff;

public class BuffActionDisarm implements BuffActions {
    private static BuffActionDisarm obj ;
    public static BuffActionDisarm getBuffAction(){
        if (obj==null) obj = new BuffActionDisarm() ;
        return obj;
    }

    public void affect(Buff buff){
        buff.getTarget().setCanCounterAttack(false);
    }

    @Override
    public void destroy(Buff buff){
        buff.getTarget().setCanCounterAttack(true);
    }
}
