package Model.item;

import Model.card.spell.Target;

public class Usable extends Item {
    int price;
    public Usable(String name, int price, int duration, int perk, ItemTarget target, ItemAction action) {
        super(name, action);
        this.price = price;
    }
    //TODO:Item ID

    @Override
    public void deploy() {

    }
}
