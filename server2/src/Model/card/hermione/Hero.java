package Model.card.hermione;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.account.Deck;
import Model.card.spell.Spell;
import View.Listeners.OnHeroDetailsPresentedListener;
import exeption.CantSpecialPowerCooldownException;
import exeption.InvalidCardException;
import exeption.InvalidCellException;

import java.util.ArrayList;

public class Hero extends Hermione {

    private int cooldown;
    private int remainCoolDOwnTime;


    public void spawn(Cell cell) {
        super.spawn(cell);
        remainCoolDOwnTime = cooldown - 1 ;
    }


    @Override
    public void applySpecialPower(Cell cell) throws InvalidCellException, InvalidCardException , CantSpecialPowerCooldownException {
        if (this.remainCoolDOwnTime != cooldown) throw new CantSpecialPowerCooldownException() ;
        super.applySpecialPower(cell);
        this.SpecialPower.deploy(Battle.getMenu().getPlayer(), Battle.getMenu().getEnemyPlayer(), cell);
        this.decreaseRemainCoolDown();
    }


    public Hero(String name, int price, int healthPoint, int attackPoint,
                AttackType attackType, int range, Spell specialPower, int manaPoint, int cooldown, String info) {
        super(name, price, manaPoint, healthPoint, attackPoint, specialPower, attackType, range, info);
        this.cooldown = cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    private void decreaseRemainCoolDown() {
        this.remainCoolDOwnTime--;
        if (this.remainCoolDOwnTime <= 0) this.remainCoolDOwnTime = this.cooldown;
    }
    public void handleCoolDown(){
        if(this.cooldown!=this.remainCoolDOwnTime){
            this.decreaseRemainCoolDown();
        }
    }

    @Override
    public void die(){
        super.die();
    }

    private static ArrayList<OnHeroDetailsPresentedListener> heroDetailsPresenters=new ArrayList<>();

    public static void addOnHeroDetailPresented(OnHeroDetailsPresentedListener presenter){
        Hero.heroDetailsPresenters.add(presenter);
    }

    public static ArrayList<OnHeroDetailsPresentedListener> getHeroDetailsPresenters() {
        return heroDetailsPresenters;
    }
}
