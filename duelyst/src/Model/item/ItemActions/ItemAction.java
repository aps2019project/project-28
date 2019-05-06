package Model.item.ItemActions;

import Model.Map.Cell;
import Model.item.Item;
import exeption.InvalidCellException;

public interface ItemAction {
   default void deploy(Item item, Cell[] target) throws InvalidCellException {}
    default void deploy(Item item) throws  InvalidCellException {}
}
