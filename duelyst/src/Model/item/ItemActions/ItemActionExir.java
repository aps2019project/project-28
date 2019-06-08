package Model.item.ItemActions;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.item.Item;
import exeption.InvalidCellException;

public class ItemActionExir implements ItemAction {
    static private ItemAction30chicken obj;

    public static ItemAction30chicken getItemAction() {
        if (obj == null) obj = new ItemAction30chicken();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            Hermione card = cell.getCardOnCell() ;
            if (card != null) {
                card.changeHealthPoint(item.getPerk());
                Buff buff = new Buff(1, false, BuffActionAP.getBuffAction(), item.getPerk());
                try {
                    buff.deploy(Battle.getMenu().getPlayer(), card);
                }catch (InvalidCellException ignored){
                    System.err.println("item Action Exir InvalidCellException while deploying APBuff!");
                }
            }
        }
    }
}
