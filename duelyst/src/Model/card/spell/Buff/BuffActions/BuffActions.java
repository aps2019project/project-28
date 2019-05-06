package Model.card.spell.Buff.BuffActions;

import Model.card.spell.Buff.Buff;
import exeption.InvalidCellException;

public interface BuffActions {
    void affect(Buff buff) throws InvalidCellException;

    default void destroy(Buff buff) {
    }
}

