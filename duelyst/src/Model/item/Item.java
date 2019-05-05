package Model.item;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.hermione.Hermione;
import Model.card.spell.Target;
import exeption.InvalidCellException;
import exeption.InvalidItemException;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Item {

    private static ArrayList<Item> items = new ArrayList<>();
    private String name;
    private Target target;
    private int perk;
    private int perk2;
    private ArrayList<ItemAction> actions = new ArrayList<>();
    private int itemID;
    private ArrayList<OnItemDetailPresentedListener> itemDetailPresenters = new ArrayList<>();

    public Item(String name, ArrayList<ItemAction> actions, Target target, int perk) {
        this.name = name;
        this.actions = actions;
        this.target = target;
        this.perk = perk;
//        this.itemID = itemID;TODO ITEMID
    }

    public Item(Item item){

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

    public void deploy() {
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
