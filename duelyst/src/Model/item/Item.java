package Model.item;

import Model.account.Player;
import Model.card.hermione.Hermione;

public abstract class Item {

    String name;
    String effect;//no ha ba space joda mishan
    int itemID;

    public Item(String name, String effect, int itemID){
        this.name = name;
        this.effect = effect;
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public int getItemID() {
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
}
