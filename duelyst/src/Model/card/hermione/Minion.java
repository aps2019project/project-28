package Model.card.hermione;

import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.Map;
import exeption.*;

public class Minion extends Hermione{
    private SPATime SPActivationTime;
    private int spawnTurn;
    // TODO: 5/15/19 ArshiA check for SPATime handling

    public Minion(String name, int price, int manaPoint, int healthPoint, int attackPoint, AttackType attackType, int range,
                  Model.card.spell.SpecialPower specialPower,SPATime SPActivationTime, String info) {
        super(name, price, manaPoint, healthPoint, attackPoint, specialPower, attackType, range, info);
        this.SPActivationTime = SPActivationTime;
    }

    @Override
    public void spawn(Cell cell){
        super.spawn(cell);
        this.spawnTurn= Battle.getMenu().getOriginalTurn();
        Battle.getMenu().getAccount().getPlayer().getMinionsInGame().add(this);
        this.itIsTime(SPATime.SPAWN);

    }

    @Override
    public void die(){
        if (this.buffEffects.isHasTheDeathCurse()){
            int distance = Map.getManhattanDistance(location , Battle.getMenu().getEnemyPlayer().getDeck().getHero().getLocation()) ;
            Hermione theTarget = Battle.getMenu().getEnemyPlayer().getDeck().getHero() ;
            for (Minion minion : Battle.getMenu().getEnemyPlayer().getMinionsInGame()){
                if (distance > Map.getManhattanDistance(location , minion.getLocation())) {
                    distance = Map.getManhattanDistance(location , minion.getLocation()) ;
                    theTarget = minion ;
                }
            }
            try {
                this.attack(theTarget,false);
            }catch(CantAttackException e){
                //TODO
            }catch (DestinationOutOfreachException e){
                //TODO
            } catch (InvalidCellException e) {
                e.printStackTrace();
            }
        }
        this.itIsTime(SPATime.DEATH);
        super.die();
    }

        @Override
        public void attack(Hermione enemyCard,boolean isComboAttack) throws DestinationOutOfreachException, CantAttackException, InvalidCellException {
            if(Battle.getMenu().getOriginalTurn()<=this.spawnTurn+1)throw new CantAttackException();
            this.itIsTime(SPATime.ATTACK);
            super.attack(enemyCard,isComboAttack);
        }

        @Override
        public void counterAttack(Hermione enemyCard) {
            this.itIsTime(SPATime.DEFEND);
            super.counterAttack(enemyCard);
        }

        public void itIsTime(SPATime currentState){
            try {
                if (this.SPActivationTime == null || !this.SPActivationTime.equals(currentState) || this.SPActivationTime==SPATime.NULL) return;
                this.applySpecialPower(this.getLocation());
            }catch(InvalidCellException | InvalidCardException ignored){}
        }


    @Override
    public boolean move(int x, int y) throws MoveTrunIsOverException, DestinationOutOfreachException, InvalidCellException, CardCantBeMovedException, DestinationIsFullException {
        if(Battle.getMenu().getOriginalTurn()<=this.spawnTurn+1)throw new CardCantBeMovedException();
        return super.move(x, y);
    }

    @Override
        public void applySpecialPower(Cell cell) throws InvalidCardException , InvalidCellException{
        this.SpecialPower.deploy(Battle.getMenu().getPlayer() , Battle.getMenu().getEnemyPlayer() , cell);
    }
        public SPATime getSPActivationTime() {
        return SPActivationTime;
    }

}
