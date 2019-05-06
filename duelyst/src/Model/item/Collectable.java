package Model.item;

import Model.card.spell.Target;
import Model.item.ItemActions.ItemAction;

public class Collectable extends Item {
    public Collectable(String name, int duration, int perk, Target target, ItemAction... itemActions){
        super(name, duration, perk, target, itemActions);
    }
}
