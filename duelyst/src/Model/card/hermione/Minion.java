package Model.card.hermione;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import exeption.CantAttackException;
import exeption.DestinationOutOfreachException;
import exeption.InvalidCardException;

public class Minion extends Hermione {
    private SPATime SPActivationTime;

    public Minion(String name, int price, int manaPoint, int healthPoint, int attackPoint, AttackType attackType, int range, Model.card.spell.SpecialPower specialPower, SPATime SPActivationTime) {
        super(name, price, manaPoint, healthPoint, attackPoint, specialPower, attackType, range);
        this.SPActivationTime = SPActivationTime;
    }

    @Override
    public void spawn(Cell cell) {
        super.spawn(cell);
        Game.battle.getAccount().getPlayer().getMinionsInGame().add(this);
        this.itIsTime(SPATime.SPAWN);

    }

    @Override
    public void die() throws InvalidCardException {
        if (this.hasTheDeathCurse) {
            int distance = Map.getManhattanDistance(location, Game.battle.getEnemyPlayer().getDeck().getHero().getLocation());
            Hermione theTarget = Game.battle.getEnemyPlayer().getDeck().getHero();
            for (Minion minion : Game.battle.getEnemyPlayer().getMinionsInGame()) {
                if (distance > Map.getManhattanDistance(location, minion.getLocation())) {
                    distance = Map.getManhattanDistance(location, minion.getLocation());
                    theTarget = minion;
                }
            }
            try {
                this.attack(theTarget);
            } catch (CantAttackException e) {
                //TODO
            } catch (DestinationOutOfreachException e) {
                //TODO
            }
        }
        this.itIsTime(SPATime.ATTACK);
        super.die();
    }

    @Override
    public void attack(Hermione enemyCard) throws DestinationOutOfreachException, CantAttackException, InvalidCardException {
        this.itIsTime(SPATime.ATTACK);
        super.attack(enemyCard);
    }

    @Override
    public void counterAttack(Hermione enemyCard) {
        this.itIsTime(SPATime.DEFEND);
        super.counterAttack(enemyCard);
    }

    public void itIsTime(SPATime currentState) {
        if (!this.SPActivationTime.equals(currentState)) return;
        this.applySpecialPower(this.getLocation().getX(), this.getLocation().getY());
    }


    @Override
    public boolean applySpecialPower(int x, int y) {
        return false;
    }

    public SPATime getSPActivationTime() {
        return SPActivationTime;
    }
}
