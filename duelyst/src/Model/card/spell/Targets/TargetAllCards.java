package Model.card.spell;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.hermione.Minion;

public class TargetAllCards{
     static Cell[] getTarget(Player player){
        Minion[] minions = player.getMinionsInGame().toArray(new Minion[0]);
        Cell[] cells = new Cell[minions.length + 1];
        cells[0] = player.getDeck().getHero().getLocation();
        int index = 1;
        for (Minion mini : minions) {
            cells[index] = mini.getLocation();
        }
        return cells ;
    }
}
