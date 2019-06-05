package Model.card.spell.Buff.BuffActions;

import Model.card.spell.Buff.Buff;

public class BuffActionStun implements BuffActions {
    private static BuffActionStun obj;

    public static BuffActionStun getBuffAction() {
        if (obj == null) obj = new BuffActionStun();
        return obj;
    }

    public void affect(Buff buff) {
        buff.getTarget().getBuffEffects().setCanAttack(false);
        buff.getTarget().getBuffEffects().setCanMove(false);
        System.err.println("just stunned that guy : " + buff.getTarget());
    }

    @Override
    public void destroy(Buff buff) {
        buff.getTarget().getBuffEffects().setCanAttack(true);
        buff.getTarget().getBuffEffects().setCanMove(true);
    }
}
