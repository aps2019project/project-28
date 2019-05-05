package Model.item;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.hermione.Hermione;
import Model.card.spell.Target;
import exeption.InvalidItemException;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Item {

    private static ArrayList<Item> items =new ArrayList<>();
    private String name;
    private ItemTarget target ;
    private ItemAction action ;
    private int itemID;
    private ArrayList<OnItemDetailPresentedListener>itemDeatailPresenters=new ArrayList<>();

    public Item(String name, ItemAction action , ItemTarget target){
        this.name = name;
        this.action = action;
        this.target = target ;
//        this.itemID = itemID;TODO ITEMID
    }


    public static Item getItem(int itemID) throws InvalidItemException {
        for (Item item : Item.getItems()) {
            if(item.getID()==itemID)return item;
        }
        throw new InvalidItemException();
    }

    public static boolean hasItem (int itemID){
        try{
            Item.getItem(itemID);
            return true;
        } catch (InvalidItemException e) {
            return false;
        }
    }

    public static Item getItem(String name) throws InvalidItemException {
        for (Item item : Item.getItems()) {
            if(item.getName().equals(name))return item;
        }
        throw new InvalidItemException();
    }

    public static boolean hasItem(String name){
        try{
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

    public ItemAction getAction() {
        return action;
    }

    public void deploy() {}

    public void deploy(Cell cell) {}

    public void increaseHealth(int number, Hermione target){
        target.setHealthPoint(target.getHealthPoint() + number);
    }

    public void increaseAttackPoint(int number, Hermione target){
        target.setAttackPoint(target.getAttackPoint() + number);
    }

    public void increaseMana(int number, Player target){
        target.setMana(target.getMana() + number);
    }

    public void damage(int number, Hermione hermione){
        hermione.setHealthPoint(hermione.getHealthPoint() + number);
    }


    public static ArrayList<Item> getItems() {
        return (ArrayList<Item>) Collections.unmodifiableList(items);
    }

    public void addNewOnItemDeatilPresentedListener(OnItemDetailPresentedListener presenter){
        this.itemDeatailPresenters.add(presenter);
    }

    public ArrayList<OnItemDetailPresentedListener> getItemDeatailPresenters() {
        return (ArrayList<OnItemDetailPresentedListener>) Collections.unmodifiableList(itemDeatailPresenters);
    }
}