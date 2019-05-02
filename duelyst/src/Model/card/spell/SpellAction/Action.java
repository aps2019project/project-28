package Model.card.spell.SpellAction;


import Controller.Game;
import Model.Map.*;
import Model.account.Player;
import Model.card.hermione.Hermione;
import Model.card.spell.*;
import Model.card.spell.BuffActionAP;
import Model.card.spell.BuffActionHP;
import Model.card.spell.BuffActionStun;

public interface Action {
    void deploy(Spell spell, Cell... cells);
}

