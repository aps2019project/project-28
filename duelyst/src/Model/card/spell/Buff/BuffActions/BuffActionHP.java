package Model.card.spell.Buff.BuffActions;

import Model.card.spell.Buff.Buff;

public class BuffActionHP implements Model.card.spell.BuffActions {
    private static BuffActionHP obj ;
    public static BuffActionHP getBuffAction(){
        if (obj==null) obj = new BuffActionHP() ;
        return obj;
    }

    public void affect(Buff buff){
        buff.getTarget().changeHealthPoint(buff.getPerk());
    }
}