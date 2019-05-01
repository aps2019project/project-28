package Model.card.spell;

public interface BuffActions {
    void affect(Buff buff) ;
    default void destroy(Buff buff){}
}

class BuffActionPoison implements BuffActions{
    private static BuffActionPoison obj ;
    public static BuffActionPoison getBuffAction(){
        if (obj==null) obj = new BuffActionPoison() ;
        return obj;
    }

    public void affect(Buff buff){
        buff.getTarget().changeHealthPoint(-1);
    }
}

class BuffActionHolly implements BuffActions{
    private static BuffActionHolly obj ;
    public static BuffActionHolly getBuffAction(){
        if (obj==null) obj = new BuffActionHolly() ;
        return obj;
    }

    public void affect(Buff buff){
        buff.getTarget().setHasHollyBuff(true);
        //TODO sare defend bayad check beshe age HollyBuff dare yeki kamtar zarbe bokhore !
    }
    @Override
    public void destroy(Buff buff){
        buff.getTarget().setHasHollyBuff(false);
    }
}

class BuffActionAP implements BuffActions{
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

class BuffActionHP implements BuffActions{
    private static BuffActionHP obj ;
    public static BuffActionHP getBuffAction(){
        if (obj==null) obj = new BuffActionHP() ;
        return obj;
    }

    public void affect(Buff buff){
        buff.getTarget().changeHealthPoint(buff.getPerk());
    }
}

class BuffActionStun implements BuffActions{
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

class BuffActionDisarm implements BuffActions{
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
