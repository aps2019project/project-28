package Model.item;

import Model.card.spell.Target;
import Model.item.ItemAction.ItemAction;

import java.util.ArrayList;

public class Usable extends Item {


    int price;

    public Usable(String name, int price, int duration, int perk, Target target, ItemAction... actions) {
        super(name, duration, perk, target, actions);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public void deploy() {

    }
}
