package Model.card.hermione;

import Model.Map.Cell;
import Model.card.hermione.Hermione;
import Model.card.spell.AttackType;
import Model.card.spell.SpecialPower;

public class Hero extends Hermione {
    private int cooldown;

    // TODO: 4/15/19 final touches
    public void deploy(Cell cell){

        super.deploy(cell);
    }

    public Hero(int cardID, String name, int price, int manaPoint, int healthPoint, int attackPoint, Model.card.spell.SpecialPower specialPower, AttackType attackType, int range, int moveRange,int cooldown) {
        super(cardID, name, price, manaPoint, healthPoint, attackPoint, specialPower, attackType, range, moveRange);
        this.cooldown=cooldown;
    }

}
