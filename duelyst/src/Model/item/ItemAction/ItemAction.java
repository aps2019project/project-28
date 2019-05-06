package Model.item.ItemAction;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import Model.item.Item;
import exeption.CantAttackException;

public interface ItemAction {
   default void deploy(Item item, Cell[] target) throws Exception{}
    default void deploy(Item item) throws  Exception {}
}
