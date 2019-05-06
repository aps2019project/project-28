package Model.card.spell.Buff.BuffActions;

import Model.card.spell.Buff.Buff;

public class BuffActionStun implements BuffActions {
    private static BuffActionStun obj ;
    public static BuffActionStun getBuffAction(){
        if (obj==null) obj = new BuffActionStun() ;
        return obj;
    }

    public void affect(Buff buff){
        buff.getTarget().setCanAttack(false);
        buff.getTarget().setCanMove(false);
    }

    @Override
    public void destroy(Buff buff){
        buff.getTarget().setCanAttack(true);
        buff.getTarget().setCanMove(true);
    }
}
