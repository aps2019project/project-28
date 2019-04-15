package Model.card.hermione;

import Model.Map.Cell;
import Model.card.Card;

public class Minion extends Hermione{
    private String SPActivationTime;

    public Minion(int cardID, String name, int price, int manaPoint, int healthPoint, int attackPoint, Model.card.spell.SpecialPower specialPower, AttackType attackType, int range, int moveRange,String SPActivationTime) {
        super(cardID, name, price, manaPoint, healthPoint, attackPoint, specialPower, attackType, range, moveRange);
        this.SPActivationTime=SPActivationTime;
    }

    @Override
    public void spawn(Cell cell){
        this.setLocation(cell);
        this.itIsTime("spawn");
    }

    @Override
    public void die(){
        this.itIsTime("death");
        super.die();
    }

    @Override
    public void attack(Cell cell) {
        this.itIsTime("attack");
        super.attack(cell);
    }

    @Override
    public void counterAttack(Card enemyCard) {
        this.itIsTime("defend");
        super.counterAttack(enemyCard);
    }

    private boolean itIsTime(String currentState){
        if(!this.SPActivationTime.equals(currentState))return false;
        this.applySpecialPower();
        return true;
    }


    @Override
    public boolean applySpecialPower() {
        return false;
    }
}
