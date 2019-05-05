package Model.card.hermione;

import Model.Map.Cell;

public class Minion extends Hermione{
    // TODO: 5/5/19 enom for SPAtime

    private String SPActivationTime;

    public Minion(String name, int price, int manaPoint, int healthPoint, int attackPoint, AttackType attackType, int range, Model.card.spell.SpecialPower specialPower,String SPActivationTime) {
        super(name, price, manaPoint, healthPoint, attackPoint, specialPower, attackType, range);
        this.SPActivationTime=SPActivationTime;
    }

    @Override
    public void spawn(Cell cell){
        super.spawn(cell);
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
    public void counterAttack(Hermione enemyCard) {
        this.itIsTime("defend");
        super.counterAttack(enemyCard);
    }

    private boolean itIsTime(String currentState){
        if(!this.SPActivationTime.equals(currentState))return false;
        this.applySpecialPower(x, y);
        return true;
    }


    @Override
    public boolean applySpecialPower(int x, int y) {
        return false;
    }
}
