package Model.item;

import Model.card.spell.Target;
import Model.item.ItemActions.ItemAction;

public class Usable extends Item {
    int price;

    public Usable(String name, int price, int duration, int perk, Target target, ItemAction... actions) {
        super(name, duration, perk, target, actions);
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
