package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.account.Player;
import Model.card.spell.Spell;

public class ActionSacrifice implements Action {
    private static ActionSacrifice obj;

    public static ActionSacrifice getAction() {
        if (obj == null) obj = new ActionSacrifice();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) {
        for (Cell cell : cells) {
            int mhp = cell.getCardOnCell().getHealthPoint();
            cell.getCardOnCell().die();
            Player player = Game.battle.getPlayer();
            player.getDeck().getHero().setHealthPoint(player.getDeck().getHero().getHealthPoint() + mhp);
        }
    }
}
