package Model.item;

import Model.card.spell.Target;

import java.util.ArrayList;

public class Usable extends Item {
    int price;

    public Usable(Usable usable){
        super(usable);
        this.price = usable.getPrice();
    }
    public Usable(String name, int price, int duration, int perk, Target target, ArrayList<ItemAction> actions) {
        super(name, actions, target, perk);
        this.price = price;
    }
    //TODO:Item ID

    public int getPrice() {
        return price;
    }

    @Override
    public void deploy() {

    }
}
