package Model.item.ItemActions;

import Controller.Game;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionDisarm;
import Model.card.spell.BuffTypes.BuffTypePassive;
import Model.item.Item;
import exeption.InvalidCellException;

public class ItemActionDisArm implements ItemAction {
    static private ItemActionDisArm obj;

    public static ItemActionDisArm getItemAction() {
        if (obj == null) obj = new ItemActionDisArm();
        return obj;
    }

    public void deploy(Item item, Cell[] target) throws InvalidCellException{
        Buff buff = new Buff(1, false , BuffActionDisarm.getBuffAction() , BuffTypePassive.getBuffTypeInstance());
        for (Cell cell : target){
            try{
                if (cell.getCardOnCell() != null)
                    buff.deploy(Game.battle.getPlayer() , cell.getCardOnCell());
            }catch(NullPointerException ignored){}
        }
    }
}