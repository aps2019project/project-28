package Model.card.spell.Targets;

        import Controller.Game;
        import Model.Map.Cell;
        import Model.account.Player;
        import Model.card.hermione.Hermione;
        import Model.card.hermione.Minion;
        import Model.card.spell.Target;
        import exeption.InvalidCellException;

        import java.util.Collections;
        import java.util.Random ;

        import java.util.ArrayList;

public class Target3RandomEnemy implements Target {
    private static TargetAllOwnCards obj;

    public static TargetAllOwnCards getTargetInstance() {
        if (obj == null) {
            obj =  new TargetAllOwnCards();
        }
        return obj ;
    }

    @Override
    public  Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player player = Game.battle.getPlayer();
        Player enemy = Game.battle.getEnemyPlayer();
        ArrayList<Hermione> hermiones = new ArrayList<>() ;
        hermiones.add(enemy.getDeck().getHero()) ;
        Collections.addAll(hermiones , (Hermione) enemy.getMinionsInGame()) ;

        if (hermiones.size() < 3) {
            throw new InvalidCellException("there are not enough enemy forces in the field for this item");
        }
        Cell[] cells = new Cell[3] ;
        Random rand = new Random() ;
        int r ;
        for (int i = 0 ; i < 3 ; i++) {
            r = rand.nextInt(hermiones.size());
            cells[i] = hermiones.get(r).getLocation();
            hermiones.remove(r);
        }
        return cells ;

    }
}
