package Model.card.spell;

import Model.card.spell.SpellAction.Action;

import java.util.ArrayList;

public class SpecialPower extends Spell {
    public SpecialPower(String name, int price, int manaPoint, int duration , int perk, Target target, Action... actions) {
        super(name, price, manaPoint, duration, perk, target, actions);
    }

    public SpecialPower(SpecialPower specialPower){
        super(specialPower);
    }
}
