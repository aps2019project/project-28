package Model.item;

import Model.account.Player;
import Model.card.hermione.Hermione;
import exeption.InvalidItemException;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Item {

    private static ArrayList<Item> items =new ArrayList<>();
    String name;
    String effect;//no ha ba space joda mishan
    int itemID;

    public Item(String name, String effect, int itemID){
        this.name = name;
        this.effect = effect;
        this.itemID = itemID;
    }


    public static Item getItem(int itemID) throws InvalidItemException {
        for (Item item : Item.getItems()) {
            if(item.getID()==itemID)return item;
        }
        throw new InvalidItemException();
    }

    public static boolean hasItem(int itemID){
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

    public String getEffect() {
        return effect;
    }

    public abstract void deploy();

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
}
