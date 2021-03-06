package Model.item.ItemActions;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.item.Item;
import exeption.InvalidCellException;

public class ItemActionChangeAPBuff implements ItemAction {
    static private ItemActionChangeAPBuff obj;

    public static ItemActionChangeAPBuff getItemAction() {
        if (obj == null) obj = new ItemActionChangeAPBuff();
        return obj;
    }

    public void deploy(Item item , Cell[] target) throws InvalidCellException {
        for (Cell cell : target) {
            if (cell.getCardOnCell() != null) {
                Buff buff = new Buff(1, false, BuffActionAP.getBuffAction(), item.getPerk());
                buff.deploy(Battle.getMenu().getPlayer(), cell.getCardOnCell());
            }
        }
    }
}
