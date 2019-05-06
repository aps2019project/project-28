package Model.card.hermione;

import Model.Map.Map;

public class Range implements AttackType {


    @Override
    public boolean canReach(Hermione champCard, Hermione enemyCard) {
        if (Map.getManhattanDistance(champCard.getLocation(), enemyCard.getLocation()) > champCard.range) return false;
        if (Map.getRadiusDistance(champCard.getLocation(), enemyCard.getLocation()) == 1) return false;
        return true;
    }
}
