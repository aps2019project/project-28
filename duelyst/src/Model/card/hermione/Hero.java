package Model.card.hermione;

import Model.Map.Cell;

public class Hero extends Hermione {

    private int cooldown;
    private int remainCoolDOwnTime;

    // TODO: 4/15/19 final touches

    // TODO: 4/15/19 final touches
    public void spawn(Cell cell){
        this.setLocation(cell);
    }

    @Override
    public boolean applySpecialPower(int x, int y) {

        // TODO: 4/15/19 saE

        return false;
    }


    public Hero(String name, int price, int healthPoint, int attackPoint,
                AttackType attackType, int range, Model.card.spell.SpecialPower specialPower, int manaPoint,int cooldown) {
        super(name, price, manaPoint, healthPoint, attackPoint, specialPower, attackType, range);
        this.cooldown=cooldown;
    }

    public Hero(Hero hero){
        super(hero);
        this.setCooldown(hero.getCooldown());
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void increaseRemainCoolDown() {
        this.remainCoolDOwnTime--;
        if(this.remainCoolDOwnTime==0)this.remainCoolDOwnTime=this.cooldown;
    }
}
