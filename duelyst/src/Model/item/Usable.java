package Model.item;

import Model.card.spell.Target;
import Model.item.ItemActions.ItemAction;

public class Usable extends Item {


    int price;

    public Usable(String name, int price, int duration, int perk, String info, Target target, ItemAction... actions) {
        super(name, duration, perk, info, target, actions);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public void deploy() {

    }
}
