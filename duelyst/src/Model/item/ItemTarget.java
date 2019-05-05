package Model.item;

import Controller.Game;
import Model.Map.Cell;
import Model.account.Player;
import exeption.InvalidCellException;

public interface ItemTarget {
    Cell[] getTarget(Player player , Cell cell) throws InvalidCellException;
}

public class ItemTargetHero implements ItemTarget {
    private ItemTargetHero obj ;
    public ItemTargetHero getTargetClass() {
        if (obj == null) obj = new ItemTargetHero() ;
        return obj ;
    }

    @Override
    public Cell[] getTarget(Player player , Cell cell) throws InvalidCellException {
        if (!cell.getCardOnCell().equals(player.getDeck().getHero())){
            throw new InvalidCellException() ;
        }
        return new Cell[] { player.getDeck().getHero().getLocation() } ;
    }
}

public class ItemTargetPlayersHermione implements ItemTarget {
    private ItemTargetPlayersHermione obj ;
    public ItemTargetPlayersHermione getTargetClass() {
        if (obj == null) obj = new ItemTargetPlayersHermione() ;
        return obj ;
    }

    @Override
    public Cell[] getTarget(Player player , Cell cell) throws InvalidCellException {
        if (!cell.getCardOnCell().getSuperCollection().getOwner().getPlayer().equals(player)){
            throw new InvalidCellException() ;
        }
        return new Cell[] { player.getDeck().getHero().getLocation() } ;
    }
}

public class ItemTargetEnemyHermione implements ItemTarget {
    private ItemTargetEnemyHermione obj ;
    public ItemTargetEnemyHermione getTargetClass() {
        if (obj == null) obj = new ItemTargetEnemyHermione() ;
        return obj ;
    }

    @Override
    public Cell[] getTarget(Player player , Cell cell) throws InvalidCellException {
        return ItemTargetPlayersHermione(Game.battle.getEnemy(player.getUser()))
    }
}