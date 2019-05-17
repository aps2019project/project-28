package Model.card.spell.Buff.BuffActions;

import Model.card.spell.Buff.Buff;

public class BuffActionHolly implements BuffActions {
    private static BuffActionHolly obj ;

    public static BuffActionHolly getBuffAction(){
        if (obj==null) obj = new BuffActionHolly() ;
        return obj;
    }

    public void affect(Buff buff){
        buff.getTarget().setHollyBuffLevel(buff.getTarget().getHollyBuffLevel());
        System.err.println("just did some HollyBuff stuff : " + buff.getPerk() + " on " + buff.getTarget().getName());
        //TODO @ArshiA sare defend bayad check beshe age HollyBuff dare yeki kamtar zarbe bokhore !
    }
    @Override
    public void destroy(Buff buff){
        buff.getTarget().setHollyBuffLevel(0);
        System.err.println("destroyed hollyBuff");
    }
}
