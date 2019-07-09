package Model.item.ItemActions;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.card.spell.Buff.BuffActions.BuffActionHP;
import Model.item.Item;
import exeption.InvalidCellException;

public class ItemActionChangeHP implements ItemAction {
    static private ItemActionChangeHP obj;

    public static ItemActionChangeHP getItemAction() {
        if (obj == null) obj = new ItemActionChangeHP();
        return obj;
    }

    public void deploy(Item item , Cell[] target) throws InvalidCellException {
        for (Cell cell : target) {
            if (cell.getCardOnCell() != null) {
                cell.getCardOnCell().changeHealthPoint(item.getPerk());
            }
        }
    }
}
