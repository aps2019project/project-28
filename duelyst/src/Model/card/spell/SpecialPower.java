package Model.card.spell;

import Model.card.spell.SpellAction.Action;

public class SpecialPower extends Spell {
    public SpecialPower(String name, int price, int manaPoint, Action... actions) {
        super(name, price, manaPoint, actions);
    }
}
