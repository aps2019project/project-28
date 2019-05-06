package Model.item;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.spell.Target;
import Model.item.ItemActions.ItemAction;
import exeption.InvalidCellException;
import exeption.InvalidItemException;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Item {

    private static ArrayList<Item> items = new ArrayList<>();
    private String name;
    private Target target;
    private int duration;
    private int perk;
    private int perk2;
    private ArrayList<ItemAction> actions = new ArrayList<>();
    private int itemID;
    private ArrayList<OnItemDetailPresentedListener> itemDetailPresenters = new ArrayList<>();

    public Item(String name, int duration, int perk, Target target, ItemAction... actions) {
        this.name = name;
        Collections.addAll(this.actions, actions);
        this.target = target;
        this.perk = perk;
        this.duration = duration;
        this.itemID = Card.uniqueID++;
    }

    public static Item getItem(int itemID) throws InvalidItemException {
        for (Item item : Item.getItems()) {
            if (item.getID() == itemID) return item;
        }
        throw new InvalidItemException();
    }

    public static boolean hasItem(int itemID) {
        try {
            Item.getItem(itemID);
            return true;
        } catch (InvalidItemException e) {
            return false;
        }
    }

    public static Item getItem(String name) throws InvalidItemException {
        for (Item item : Item.getItems()) {
            if (item.getName().equals(name)) return item;
        }
        throw new InvalidItemException();
    }

    public static boolean hasItem(String name) {
        try {
            Item.getItem(name);
            return true;
        } catch (InvalidItemException e) {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return itemID;
    }

    public ArrayList<ItemAction> getAction() {
        return actions;
    }

    public void deploy() throws Exception {
        for (ItemAction action : actions) {
            action.deploy(this);
        }
    }

    public void deploy(Cell cell) throws InvalidCellException {
        try {
            target.getTarget(cell);
        } catch (InvalidCellException e) {
            throw e;
        }
        for (ItemAction action : actions) {
            try {
                // TODO: 5/6/19 saE ya Fattme handle this error
                action.deploy(this, target.getTarget(cell));
            } catch (InvalidCellException e) {
                throw e;
            }
        }
    }

    public void increaseHealth(int number, Hermione target) {
        target.setHealthPoint(target.getHealthPoint() + number);
    }

    public void increaseAttackPoint(int number, Hermione target) {
        target.setAttackPoint(target.getAttackPoint() + number);
    }

    public void increaseMana(int number, Player target) {
        target.setMana(target.getMana() + number);
    }

    public void damage(int number, Hermione hermione) {
        hermione.setHealthPoint(hermione.getHealthPoint() + number);
    }


    public static ArrayList<Item> getItems() {
        return (ArrayList<Item>) Collections.unmodifiableList(items);
    }

    public void addNewOnItemDeatilPresentedListener(OnItemDetailPresentedListener presenter) {
        this.itemDetailPresenters.add(presenter);
    }

    public ArrayList<OnItemDetailPresentedListener> getItemDetailPresenters() {
        return (ArrayList<OnItemDetailPresentedListener>) Collections.unmodifiableList(itemDetailPresenters);
    }

    public int getPerk() {
        return perk;
    }

    public void setPerk(int perk) {
        this.perk = perk;
    }

    public void setPerk2(int perk) {
        this.perk2 = perk;
    }

}
