package Model.card.spell;

import Model.card.spell.SpellAction.Action;

import java.util.ArrayList;

public class SpecialPower extends Spell {
    public SpecialPower(String name, int price, int manaPoint, int duration , int perk, String info, Target target, Action action) {
        super(name, price, manaPoint, duration, perk, info, target, action);
    }
}
