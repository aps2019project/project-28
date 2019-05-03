package Model.card.spell;


import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.hermione.Minion;
import exeption.InvalidCellException;

public interface Target {
    Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws InvalidCellException;
}


