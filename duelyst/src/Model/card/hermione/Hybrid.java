package Model.card.hermione;

import Model.Map.Map;
import Model.card.Card;

import java.security.PublicKey;

public class Hybrid implements AttackType {

    @Override
    public boolean canReach(Hermione champCard, Hermione enemyCard) {
        if(Map.getManhattanDistance(champCard.getLocation(),enemyCard.getLocation())<=champCard.getRange())return true;
        return false;
    }
    public Hybrid(){

    }
    public Hybrid(Hybrid hybrid){

    }
}
