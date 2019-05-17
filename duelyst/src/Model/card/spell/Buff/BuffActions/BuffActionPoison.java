package Model.card.spell.Buff.BuffActions;

import Model.card.spell.Buff.Buff;

public class BuffActionPoison implements BuffActions {
    private static BuffActionPoison obj;

    public static BuffActionPoison getBuffAction() {
        if (obj == null) obj = new BuffActionPoison();
        return obj;
    }

    public void affect(Buff buff) {
        buff.getTarget().changeHealthPoint(-1);
        System.err.println("just changed some poisoning " + "on " + buff.getTarget().getName());
    }
}
