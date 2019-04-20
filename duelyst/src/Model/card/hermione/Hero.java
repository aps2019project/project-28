package Model.card.hermione;

import Model.Map.Cell;

public class Hero extends Hermione {

    private int cooldown;
    private int remainCoolDOwnTime;

    // TODO: 4/15/19 final touches




    public Hero(int cardID, String name, int price, int manaPoint, int healthPoint, int attackPoint, Model.card.spell.SpecialPower specialPower, AttackType attackType, int range, int moveRange,int cooldown) {
        super(cardID, name, price, manaPoint, healthPoint, attackPoint, specialPower, attackType, range, moveRange);
        this.cooldown=cooldown;
    }

    @Override
    public boolean applySpecialPower() {

        return false;
    }
}
