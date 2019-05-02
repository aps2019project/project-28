package Model.card.spell;

import Model.card.spell.Buff.Buff;

public interface BuffActions {
    void affect(Buff buff) ;
    default void destroy(Buff buff){}
}

