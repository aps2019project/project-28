package Model.item;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.card.spell.Buff.BuffActions.BuffActionHolly;
import exeption.CantAttackException;

public interface ItemAction {
   default void deploy(Item item, Cell[] target) throws Exception{}
    default void deploy(Item item) throws  Exception {}
}

class ItemActionExtraMana implements ItemAction {
    static private ItemActionExtraMana obj;

    public ItemActionExtraMana getItemAction() {
        if (obj == null) obj = new ItemActionExtraMana();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        if (Game.battle.getOriginalTurn() > 2) Game.battle.getPlayer().setMana(Game.battle.getPlayer().getMana() + 1);
    }
}

class ItemActionShieldAF implements ItemAction {
    static private ItemActionShieldAF obj;

    public ItemActionShieldAF getItemAction() {
        if (obj == null) obj = new ItemActionShieldAF();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        Buff buff = new Buff(-1, true, BuffActionHolly.getBuffAction()) ;
        Buff buff2 = new Buff(-1, true, BuffActionHolly.getBuffAction()) ;
        buff.deploy(Game.battle.getPlayer() , Game.battle.getPlayer().getDeck().getHero());
        buff2.deploy(Game.battle.getPlayer() , Game.battle.getPlayer().getDeck().getHero());
    }
}

class ItemActionDamoolArch implements ItemAction {
    static private ItemActionDamoolArch obj;

    public ItemActionDamoolArch getItemAction() {
        if (obj == null) obj = new ItemActionDamoolArch();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            cell.getCardOnCell().setRange(cell.getCardOnCell().getRange() + item.getPerk());
        }
    }
}

class ItemActionjoonBaw implements ItemAction {
    static private ItemActionjoonBaw obj;

    public ItemActionjoonBaw getItemAction() {
        if (obj == null) obj = new ItemActionjoonBaw();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            cell.getCardOnCell().changeHealthPoint(item.getPerk());
        }
    }
}

class ItemAction3HornedArrow implements ItemAction {
    static private ItemAction3HornedArrow obj;

    public ItemAction3HornedArrow getItemAction() {
        if (obj == null) obj = new ItemAction3HornedArrow();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            cell.getCardOnCell().changeAttackPoint(item.getPerk());
        }
    }
}

class ItemAction30chicken implements ItemAction {
    static private ItemAction30chicken obj;

    public ItemAction30chicken getItemAction() {
        if (obj == null) obj = new ItemAction30chicken();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            if (cell.getCardOnCell().getHealthPoint() < 15){
                cell.getCardOnCell().setHealthPoint(cell.getCardOnCell().getHealthPoint() * 2);
            }
        }
    }
}

class ItemActionExir implements ItemAction {
    static private ItemAction30chicken obj;

    public ItemAction30chicken getItemAction() {
        if (obj == null) obj = new ItemAction30chicken();
        return obj;
    }

    public void deploy(Item item, Cell[] target){
        for (Cell cell : target){
            Hermione card = cell.getCardOnCell() ;
            card.changeHealthPoint(item.getPerk());
            card.changeAttackPoint(item.getPerk());
        }
    }
}

class ItemActionManaGiver implements ItemAction {
    static private ItemActionManaGiver obj;

    public ItemActionManaGiver getItemAction() {
        if (obj == null) obj = new ItemActionManaGiver();
        return obj;
    }

    public void deploy(Item item){
        Player player = Game.battle.getPlayer();
        player.setMana(player.getMana() + item.getPerk());
    }
}

class ItemActionRooEnTan implements ItemAction {
    static private ItemActionRooEnTan obj;

    public ItemActionRooEnTan getItemAction() {
        if (obj == null) obj = new ItemActionRooEnTan();
        return obj;
    }

    public void deploy(Item item , Cell[] target){
       for (Cell cell : target) {
           Buff buff = new Buff (2 , true , BuffActionHolly.getBuffAction() , 10) ;
           buff.deploy(Game.battle.getPlayer() , cell.getCardOnCell());
       }
    }
}

class ItemActionMinionRandomAttacker implements ItemAction {
    static private ItemActionMinionRandomAttacker obj;

    public ItemActionMinionRandomAttacker getItemAction() {
        if (obj == null) obj = new ItemActionMinionRandomAttacker();
        return obj;
    }

    public void deploy(Item item , Cell[] target) {
        for (Cell cell : target) {
            cell.getCardOnCell().setHasTheDeathCurse(true);
        }
    }
}

class ItemActionRandomDamage implements ItemAction {
    static private ItemActionRandomDamage obj;

    public ItemActionRandomDamage getItemAction() {
        if (obj == null) obj = new ItemActionRandomDamage();
        return obj;
    }

    public void deploy(Item item , Cell[] target){
        for (Cell cell : target) {
            cell.getCardOnCell().changeHealthPoint(item.getPerk());
        }
    }
}

class ItemActionChangeAP implements ItemAction {
    static private ItemActionChangeAP obj;

    public ItemActionChangeAP getItemAction() {
        if (obj == null) obj = new ItemActionChangeAP();
        return obj;
    }

    public void deploy(Item item , Cell[] target){
        for (Cell cell : target) {
            Buff buff = new Buff(1 , false , BuffActionAP.getBuffAction() , 2) ;
            buff.deploy(Game.battle.getPlayer() , cell.getCardOnCell());
        }
    }
}

class ItemActionKingSlayer implements ItemAction {
    static private ItemActionChangeAP obj;

    public ItemActionChangeAP getItemAction() {
        if (obj == null) obj = new ItemActionChangeAP();
        return obj;
    }

    public void deploy(Item item){
        Game.battle.getPlayer().setMaxMana(Game.battle.getPlayer().getMaxMana());

    }
}