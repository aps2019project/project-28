package Model.card.hermione;

import Model.Map.Map;
import Model.card.Card;

public class Melee implements AttackType {

    @Override
    public boolean canReach(Hermione champCard, Hermione enemyCard) {
        return Map.getRadiusDistance(champCard.getLocation(), enemyCard.getLocation()) <= 1  ;

    }
}
