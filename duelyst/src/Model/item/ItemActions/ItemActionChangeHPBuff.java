package Model.item.ItemActions;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionHP;
import Model.item.Item;
import exeption.InvalidCellException;

public class ItemActionChangeHPBuff implements ItemAction {
    static private ItemActionChangeHPBuff obj;

    public static ItemActionChangeHPBuff getItemAction() {
        if (obj == null) obj = new ItemActionChangeHPBuff();
        return obj;
    }

    public void deploy(Item item , Cell[] target) throws InvalidCellException {
        for (Cell cell : target) {
            if (cell.getCardOnCell() != null) {
                Buff buff = new Buff(1, false, BuffActionHP.getBuffAction(), item.getPerk());
                buff.deploy(Battle.getMenu().getPlayer(), cell.getCardOnCell());
            }
        }
    }
}
