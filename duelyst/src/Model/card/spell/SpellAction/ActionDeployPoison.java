package Model.card.spell.SpellAction;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionPoison ;
import Model.card.spell.Spell;
import exeption.InvalidCellException;

public class ActionDeployPoison implements Model.card.spell.SpellAction.Action {
    private static ActionDeployPoison obj;

    public static ActionDeployPoison getAction() {
        if (obj == null) obj = new ActionDeployPoison();
        return obj;
    }

    @Override
    public void deploy(Spell spell, Cell... cells) throws InvalidCellException{
        for (Cell cell : cells) {
            Buff poisonBuff = new Buff(spell.getDuration(spell.getIndexOfAction(ActionDeployPoison.getAction())), false, BuffActionPoison.getBuffAction());
            poisonBuff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }

    public void deploy(int duration , Cell... cells) throws InvalidCellException{
        for (Cell cell : cells) {
            Buff poisonBuff = new Buff(duration, false, BuffActionPoison.getBuffAction());
            poisonBuff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
        }
    }
}




