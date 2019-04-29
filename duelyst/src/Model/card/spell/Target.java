package Model.card.spell;


import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.hermione.Minion;

import javax.swing.text.View;

public interface Target {
    public void deploy(Player player, Player enemy, Cell cell, Spell spell);
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        spell.action.deploy(spell, cell);
    }
}

class TargetAllCards {

    public void deploy(Player player, Cell cell, Spell spell) {
        Minion[] minions = player.getMinionsInGame().toArray(new Minion[0]);
        Cell[] cells = new Cell[minions.length + 1];
        cells[0] = player.getHero().getLocation();
        int index = 1;
        for (Minion mini : minions) {
            cells[index] = mini.getLocation();
        }
        spell.action.deploy(spell, cells);
    }
}

class TargetAllEnemyCards extends TargetAllCards implements Target {
    private static TargetAllEnemyCards obj;

    public static TargetAllEnemyCards getTarget() {
        if (obj == null) {
            obj =  new TargetAllEnemyCards();
        }
        return obj ;
    }

    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getHero().getLocation()) {
            View.;
            return;
        }
        super.deploy(enemy, cell, spell);

    }
}

class TargetAllOwnCards extends TargetAllCards implements Target {
    private static TargetAllOwnCards obj;

    public static TargetAllOwnCards getTarget() {
        if (obj == null) {
            obj =  new TargetAllOwnCards();
        }
        return obj ;
    }

    @Override
    public  void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getHero().getLocation()) {
            View.;
            return;
        }
        super.deploy(player, cell, spell);
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell.getCardOnCell() == enemy.getHero()) {
            View.;
            return;
        }
        spell.action.deploy(spell, cell);

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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        for (Minion mini : enemy.getMinionsInGame()) {
            if (mini.getLocation() == cell) {
                spell.action.deploy(spell, cell);
                return;
            }
        }
        View.;
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell.getCardOnCell() != enemy.getHero() && !enemy.getMinionsInGame().contains(cell.getCardOnCell())) {
            View.;
            return;
        }
        spell.action.deploy(spell, cell);
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getHero().getLocation()) {
            View.;
            return;
        }
        spell.action.deploy(spell, cell);
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (!player.getMinionsInGame().contains(cell.getCardOnCell())) {
            View.;
            return;
        }
        spell.action.deploy(spell, cell);
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (player.getHero().getLocation() != cell && !player.getMinionsInGame().contains(cell.getCardOnCell())) {
            View.;
            return;
        }
        spell.action.deploy(spell, cell);
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        int x = cell.getX();
        int y = cell.getY();
        Map map = Game.battle.getMap();
        if (x == Map.WIDTH || y == Map.HEIGHT) {
            View.;
            return;
        }
        Cell[] cells = {cell, map.getCell(x, y + 1), map.getCell(x + 1, y), map.getCell(x + 1, y + 1)};
        spell.action.deploy(spell, cells);
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        int x = cell.getX();
        int y = cell.getY();
        Map map = Game.battle.getMap();
        if (x > Map.WIDTH - 2 || y > Map.HEIGHT - 2) {
            View.;
            return;
        }
        Cell[] cells = {cell, map.getCell(x, y + 1), map.getCell(x, y + 2), map.getCell(x + 1, y),
                map.getCell(x + 2, y), map.getCell(x + 1, y + 1), map.getCell(x + 1, y + 2),
                map.getCell(x + 2, y + 1), map.getCell(x + 2, y + 2)};

        spell.action.deploy(spell, cells);
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getHero().getLocation()) {
            View.;
            return;
        }
        Map map = Game.battle.getMap();
        int x = cell.getX();
        int y = cell.getY();
        Cell[] cells = {map.getCell(x - 1, y - 1), map.getCell(x, y - 1), map.getCell(x + 1, y - 1),
                map.getCell(x - 1, y), map.getCell(x + 1, y), map.getCell(x - 1, y + 1),
                map.getCell(x, y + 1), map.getCell(x + 1, y + 1)};

        spell.action.deploy(spell, cells);
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != player.getHero().getLocation()) {
            View.;
            return;
        }
        Map map = Game.battle.getMap();
        Cell[] cells = new Cell[Map.WIDTH];
        int y = cell.getY();
        for (int x = 1; x <= Map.WIDTH; x++) {
            cells[x - 1] = map.getCell(x, y);
        }
        spell.action.deploy(spell, cells);
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
    public void deploy(Player player, Player enemy, Cell cell, Spell spell) {
        if (cell != enemy.getHero().getLocation()) {
            View.;
            return;
        }
        Map map = Game.battle.getMap();
        Cell[] cells = new Cell[Map.HEIGHT];
        int x = cell.getX();
        for (int y = 1; y <= map.HEIGHT; y++) {
            cells[y - 1] = map.getCell(x, y);
        }
        spell.action.deploy(spell, cells);
    }
}


