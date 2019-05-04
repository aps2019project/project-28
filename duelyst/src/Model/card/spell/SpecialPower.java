package Model.card.spell;

import Model.card.spell.SpellAction.Action;

import java.util.ArrayList;

public class SpecialPower extends Spell {
    public SpecialPower(String name, int price, int manaPoint, ArrayList<Action> actions, Target target) {
        super(name, price, manaPoint, actions, target);
    }
}
