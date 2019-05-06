package Model.card.spell.SpellAction;

import Model.Map.*;
import Model.card.spell.*;
import exeption.InvalidCardException;
import exeption.InvalidCellException;


public interface Action {
    void deploy(Spell spell, Cell... cells) throws InvalidCellException, InvalidCardException;
}

