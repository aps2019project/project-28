package Model.card.spell.Targets;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.spell.Target;
import exeption.InvalidCellException;

import java.util.ArrayList;

public class TargetHeroSurroundings implements Target {
    private static TargetHeroSurroundings obj;

    public static TargetHeroSurroundings getTargetInstance() {
        if (obj == null) {
            obj = new TargetHeroSurroundings();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Cell cell) throws InvalidCellException {
        Player player = Game.battle.getPlayer() ;
        Player enemy = Game.battle.getEnemyPlayer() ;
        if (cell != player.getDeck().getHero().getLocation())  {
            throw new InvalidCellException();
        }
        Map map = Game.battle.getMap();
        int x = cell.getX();
        int y = cell.getY();
        Cell[] cells = {map.getCell(x - 1, y - 1), map.getCell(x, y - 1), map.getCell(x + 1, y - 1),
                map.getCell(x - 1, y), map.getCell(x + 1, y), map.getCell(x - 1, y + 1),
                map.getCell(x, y + 1), map.getCell(x + 1, y + 1)};

        ArrayList<Cell> cells2 = new ArrayList<>() ;
        for (Cell cel : cells){
            if (cel.getCardOnCell() != null && cel.getCardOnCell().getSuperCollection().getOwner().getPlayer().equals(enemy)){
                cells2.add(cel) ;
            }
        }
        cells = new Cell[cells2.size()] ;
        int index = 0 ;
        for (Cell cel : cells2){
            cells[index] = cel ;
            index++ ;
        }
        return cells ;
    }
}
