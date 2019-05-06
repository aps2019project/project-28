package Model.item.ItemActions;

import Model.Map.Cell;
import Model.card.hermione.Minion;
import Model.item.Item;
import exeption.InvalidCellException;

class ItemActionGiantSnake implements ItemAction {
    static private ItemActionGiantSnake obj;

    public ItemActionGiantSnake getItemAction() {
        if (obj == null) obj = new ItemActionGiantSnake();
        return obj;
    }

    public void deploy(Item item, Cell[] target)throws InvalidCellException {
        for (Cell cell : target){
            if(cell.getCardOnCell() == null || !cell.getCardOnCell().getClass().equals(Minion.class)) throw new InvalidCellException("wtf ! itemActionGiantSnake ! this shouldn't happen !") ;
            cell.getCardOnCell().getBuffEffects().setHasTheGiantSnakeEffect(true);
        }
    }
}
