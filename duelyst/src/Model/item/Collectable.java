package Model.item;

import Model.card.spell.SpellAction.Action;
import Model.card.spell.Target;

import java.util.ArrayList;

public class Collectable extends Item {
    public Collectable(String name, ArrayList<ItemAction> itemAction, Target target, int perk){
        super(name, itemAction, target, perk);
    }

    public Collectable(Collectable collectable){
        super(collectable);
    }
}
