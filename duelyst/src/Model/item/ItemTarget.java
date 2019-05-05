package Model.item;

import Controller.Game;
import Model.Map.Cell;
import Model.account.Player;
import Model.card.hermione.Melee;
import exeption.InvalidCellException;

public interface ItemTarget {
    Cell[] getTarget(Player player , Cell cell) throws InvalidCellException;
}

public class ItemTargetHero implements ItemTarget {
    private static ItemTargetHero obj ;
    public static ItemTargetHero getTargetClass() {
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

public class ItemTargetEnemyHero implements ItemTarget {
    private static ItemTargetHero obj ;
    public static ItemTargetHero getTargetClass() {
        if (obj == null) obj = new ItemTargetHero() ;
        return obj ;
    }

    @Override
    public Cell[] getTarget(Player player , Cell cell) throws InvalidCellException {
        if (!cell.getCardOnCell().equals(player.getDeck().getHero())){
            throw new InvalidCellException() ;
        }
        return ItemTargetHero.getTargetClass().getTarget(Game.battle.getEnemyPlayer() , cell) ;
    }
}

public class ItemTargetPlayersHermione implements ItemTarget {
    private static ItemTargetPlayersHermione obj ;
    public static ItemTargetPlayersHermione getTargetClass() {
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
    private static ItemTargetEnemyHermione obj ;
    public static ItemTargetEnemyHermione getTargetClass() {
        if (obj == null) obj = new ItemTargetEnemyHermione() ;
        return obj ;
    }

    @Override
    public Cell[] getTarget(Player player , Cell cell) throws InvalidCellException {
        return ItemTargetPlayersHermione.getTargetClass().getTarget(Game.battle.getEnemy(player.getUser()) , cell) ;
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