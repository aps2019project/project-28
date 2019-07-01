package Model.item;

import Model.Map.Cell;
import Model.Primary;
import Model.account.player.Player;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.spell.Target;
import Model.item.ItemActions.ItemAction;
import View.Listeners.OnItemDetailPresentedListener;
import exeption.InvalidCellException;
import exeption.InvalidItemException;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Item {

    private static ArrayList<Item> items=Primary.items;
    private String name;
    private Target target;
    private int duration;
    private int perk;
    private int perk2;
    private ArrayList<ItemAction> actions = new ArrayList<>();
    private int itemID;
    private static ArrayList<OnItemDetailPresentedListener> itemDetailPresenters = new ArrayList<>();
    private String info;

    static{

    }

    public Item(String name, int duration, int perk, String info, Target target, ItemAction... actions) {
        this.name = name;
        Collections.addAll(this.actions, actions);
        this.target = target;
        this.perk = perk;
        this.duration = duration;
        this.info = info;
//        this.itemID = Card.uniqueID++;
    }

    public static Item getItem(int itemID) throws InvalidItemException {
        for (Item item : Item.getItems()) {
            if (item.getID() == itemID) return item;
        }
        throw new InvalidItemException();
    }

    public static boolean hasItem(int itemID) {
        for (Item i:
             items) {
            if(i.getID() == itemID){
                return true;
            }
        }
        return false;
    }

    public static Item getItem(String name) throws InvalidItemException {
        for (Item item : Item.getItems()) {
            if (item.getName().toLowerCase().equals(name)) return item;
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
        target.getTarget(cell);
        for (ItemAction action : actions) action.deploy(this, target.getTarget(cell));
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
        return (items);
    }

    public static void addNewOnItemDeatilPresentedListener(OnItemDetailPresentedListener presenter) {
        Item.itemDetailPresenters.add(presenter);
    }

    public static ArrayList<OnItemDetailPresentedListener> getItemDetailPresenters() {
        return itemDetailPresenters;
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

    public String getComment() {
        return this.info;
    }
    public void setComment(String comment){
       this.info=comment;
    }
}
