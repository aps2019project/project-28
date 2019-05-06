package Model.card.spell.Targets;

import Model.Map.Cell;
import Model.account.Player;
import Model.card.hermione.Hermione;
import Model.card.spell.Target;
import exeption.InvalidCellException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TargetRandom {
    public static Cell[] getTarget(Player player) throws InvalidCellException {
        ArrayList<Hermione> hermiones = new ArrayList<>() ;
        hermiones.add(player.getDeck().getHero()) ;
        Collections.addAll(hermiones , (Hermione) player.getMinionsInGame()) ;
        Random rand = new Random() ;
        int r = rand.nextInt(hermiones.size()) ;
        return new Cell[] {hermiones.get(r).getLocation()} ;
    }

    public static Target getTargetInstance() {
    }

    //TODO saee
}
