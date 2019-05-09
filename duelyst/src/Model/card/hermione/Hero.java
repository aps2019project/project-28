package Model.card.hermione;

import Controller.Game;
import Model.Map.Cell;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import View.Listeners.OnHeroDetailsPresentedListener;
import exeption.InvalidCardException;

import java.util.ArrayList;
import java.util.Collections;

public class Hero extends Hermione {

    private int cooldown;
    private int remainCoolDOwnTime;

    // TODO: 4/15/19 final touches

    // TODO: 4/15/19 final touches
    public void spawn(Cell cell) {
        this.setLocation(cell);
    }

    @Override
    public boolean applySpecialPower(int x, int y) {

        // TODO: 4/15/19 saE

        return false;
    }


    public Hero(String name, int price, int healthPoint, int attackPoint,
                AttackType attackType, int range, Model.card.spell.SpecialPower specialPower, int manaPoint, int cooldown) {
        super(name, price, manaPoint, healthPoint, attackPoint, specialPower, attackType, range);
        this.cooldown = cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void increaseRemainCoolDown() {
        this.remainCoolDOwnTime--;
        if (this.remainCoolDOwnTime == 0) this.remainCoolDOwnTime = this.cooldown;
    }

    @Override
    public void die() throws InvalidCardException {
        super.die();
        Game.battle.getEnemyPlayer().getDeck().killHero();
    }

    private static ArrayList<OnHeroDetailsPresentedListener> heroDetailsPresenters=new ArrayList<>();

    public static void addOnHeroDetailPresented(OnHeroDetailsPresentedListener presenter){
        Hero.heroDetailsPresenters.add(presenter);
    }

    public static ArrayList<OnHeroDetailsPresentedListener> getHeroDetailsPresenters() {
        return heroDetailsPresenters;
    }
}
