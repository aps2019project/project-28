package Model.card.spell;


import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.hermione.Minion;
import exeption.InvalidCellException;

import javax.swing.text.View;

public interface Target {
    Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Throwable;
}

class TargetSingleCell implements Target {
    TargetSingleCell obj;

    public TargetSingleCell getTarget() {
        if (obj == null) {
            obj =  new TargetSingleCell();
        }
        return obj ;
    }

    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) {
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}

class TargetAllCards{
     static Cell[] getTarget(Player player, Cell cell, Spell spell){
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

class TargetAllEnemyCards  implements Target {
    private static TargetAllEnemyCards obj;

    public static TargetAllEnemyCards getTarget() {
        if (obj == null) {
            obj =  new TargetAllEnemyCards();
        }
        return obj ;
    }

    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Throwable {
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        return TargetAllCards.getTarget(enemy, cell, spell);

    }
}

class TargetAllOwnCards implements Target {
    private static TargetAllOwnCards obj;

    public static TargetAllOwnCards getTarget() {
        if (obj == null) {
            obj =  new TargetAllOwnCards();
        }
        return obj ;
    }

    @Override
    public  Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Throwable {
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        TargetAllCards.getTarget(player, cell, spell);
    }
}

class TargetEnemyHero implements Target {
    private static TargetEnemyHero obj;

    public static TargetEnemyHero getTarget() {
        if (obj == null) {
            obj = new TargetEnemyHero();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Exception {
        if (cell.getCardOnCell() == enemy.getDeck().getHero()) {
            throw new InvalidCellException();
        }
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}

class TargetEnemyMinion implements Target {
    private static TargetEnemyMinion obj;

    public static TargetEnemyMinion getTarget() {
        if (obj == null) {
            obj = new TargetEnemyMinion();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Throwable{
        if (enemy.getMinionsInGame().contains(cell.getCardOnCell())){
            Cell[] cells = new Cell[1] ;
            cells[0] = cell ;
            return cells ;
        }
        else throw new InvalidCellException();
    }
}

class TargetEnemyCard implements Target {
    private static TargetEnemyCard obj;

    public static TargetEnemyCard getTarget() {
        if (obj == null) {
            obj = new TargetEnemyCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Throwable{
        if (cell.getCardOnCell() != enemy.getDeck().getHero() && !enemy.getMinionsInGame().contains(cell.getCardOnCell())) {
            throw new InvalidCellException();
        }
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}

class TargetOwnHero implements Target {
    private static TargetEnemyCard obj;

    public static TargetEnemyCard getTarget() {
        if (obj == null) {
            obj = new TargetEnemyCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Throwable{
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
        }
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}

class TargetOwnMinion implements Target {
    private static TargetEnemyCard obj;

    public static TargetEnemyCard getTarget() {
        if (obj == null) {
            obj = new TargetEnemyCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Throwable {
        if (!player.getMinionsInGame().contains(cell.getCardOnCell())) {
            throw new InvalidCellException();
        }
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}

class TargetOwnCard implements Target {
    private static TargetEnemyCard obj;

    public static TargetEnemyCard getTarget() {
        if (obj == null) {
            obj = new TargetEnemyCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Throwable {
        if (player.getDeck().getHero().getLocation() != cell && !player.getMinionsInGame().contains(cell.getCardOnCell())) {
            throw new InvalidCellException();
        }
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}

class TargetTwoByTwo implements Target {
    private static TargetEnemyCard obj;

    public static TargetEnemyCard getTarget() {
        if (obj == null) {
            obj = new TargetEnemyCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Throwable {
        int x = cell.getX();
        int y = cell.getY();
        Map map = Game.battle.getMap();
        if (x == Map.WIDTH || y == Map.HEIGHT) {
            throw new InvalidCellException();
        }
        Cell[] cells = {cell, map.getCell(x, y + 1), map.getCell(x + 1, y), map.getCell(x + 1, y + 1)};
        Cell[] cells = new Cell[1] ;
        cells[0] = cell ;
        return cells ;
    }
}

class TargetThreeByThree implements Target {
    private static TargetEnemyCard obj;

    public static TargetEnemyCard getTarget() {
        if (obj == null) {
            obj = new TargetEnemyCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) throws Throwable{
        int x = cell.getX();
        int y = cell.getY();
        Map map = Game.battle.getMap();
        if (x > Map.WIDTH - 2 || y > Map.HEIGHT - 2) {
            throw new InvalidCellException();
        }
        Cell[] cells = {cell, map.getCell(x, y + 1), map.getCell(x, y + 2), map.getCell(x + 1, y),
                map.getCell(x + 2, y), map.getCell(x + 1, y + 1), map.getCell(x + 1, y + 2),
                map.getCell(x + 2, y + 1), map.getCell(x + 2, y + 2)};

        return cells ;
    }
}

class TargetHeroSurroundings implements Target {
    private static TargetEnemyCard obj;

    public static TargetEnemyCard getTarget() {
        if (obj == null) {
            obj = new TargetEnemyCard();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
            return;
        }
        Map map = Game.battle.getMap();
        int x = cell.getX();
        int y = cell.getY();
        Cell[] cells = {map.getCell(x - 1, y - 1), map.getCell(x, y - 1), map.getCell(x + 1, y - 1),
                map.getCell(x - 1, y), map.getCell(x + 1, y), map.getCell(x - 1, y + 1),
                map.getCell(x, y + 1), map.getCell(x + 1, y + 1)};

        for (Action action : spell.actions) 
            action.deploy(spell, cells);
    }
}

class TargetOwnHeroRow implements Target {
    private static TargetOwnHeroRow obj;

    public static TargetOwnHeroRow getTarget() {
        if (obj == null) {
            obj = new TargetOwnHeroRow();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getDeck().getHero().getLocation()) {
            throw new InvalidCellException();
            return;
        }
        Map map = Game.battle.getMap();
        Cell[] cells = new Cell[Map.WIDTH];
        int y = cell.getY();
        for (int x = 1; x <= Map.WIDTH; x++) {
            cells[x - 1] = map.getCell(x, y);
        }
        for (Action action : spell.actions) 
            action.deploy(spell, cells);
    }
}

class TargetEnemyHeroColumn implements Target {
    private static TargetEnemyHeroColumn obj;

    public static TargetEnemyHeroColumn getTarget() {
        if (obj == null) {
            obj = new TargetEnemyHeroColumn();
        }
        return obj ;
    }
    @Override
    public Cell[] getTarget(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != enemy.getHero().getLocation()) {
            throw new InvalidCellException();
            return;
        }
        Map map = Game.battle.getMap();
        Cell[] cells = new Cell[Map.HEIGHT];
        int x = cell.getX();
        for (int y = 1; y <= map.HEIGHT; y++) {
            cells[y - 1] = map.getCell(x, y);
        }
        for (Action action : spell.actions) 
            action.deploy(spell, cells);
    }
}


