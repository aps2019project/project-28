package Model.item;

import Model.card.spell.SpellAction.Action;
import Model.card.spell.Target;

import java.util.ArrayList;

public class Collectable extends Item {
    public Collectable(String name, int duration, int perk, Target target, ItemAction... itemAction){
        super(name, itemAction, target, perk);
    }
}
