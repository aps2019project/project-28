package Model.Map;

import Controller.Game;
import Model.Primary;
import Model.account.Collection;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionPoison;
import Model.card.spell.BuffTypes.BuffTypePassive;
import Model.item.Collectable;
import Model.item.Flag;
import exeption.InvalidCellException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Map {
    private static ArrayList<Map> maps;
    public static final int HEIGHT = 12;
    public static final int WIDTH = 12;
    // TODO: 5/14/19 size ha ro doros kon
    private Cell[][] board = new Cell[Map.HEIGHT + 1][Map.WIDTH + 1];
    public static final int MAX_COLLECTABLE_ON_MAP=3;
    private ArrayList<Flag> flags;
    // TODO: 5/15/19 WIDTH O HIEGHT RO DOROS KON

    public static int getManhattanDistance(Cell cell1, Cell cell2) {
        return Math.abs(cell1.getX() - cell2.getX()) + Math.abs(cell1.getY() - cell2.getY());
    }

    public Cell[] getCellsInDistance(Cell cell, int distance) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (Cell[] cel1 : this.board) {
            for (Cell cel : cel1) {
                if (cel == null) continue;
                if (Map.getManhattanDistance(cell, cel) == distance) cells.add(cel);
            }
        }
        Cell[] cellsArray = new Cell[cells.size()];
        for (int i = 0; i < cells.size(); i++) {
            cellsArray[i] = cells.get(i);
        }
        return cellsArray;
    }

    public static int getRadiusDistance(Cell cell1, Cell cell2) {
        if (cell1 == null || cell2 == null) return Integer.MAX_VALUE;
        return Integer.max(Math.abs(cell1.getX() - cell2.getX()), Math.abs(cell1.getY() - cell2.getY()));

    }

    public Cell getCell(int x, int y) throws InvalidCellException {
//        System.err.println(x+" | "+y);
//        System.err.println(x > WIDTH);
//        System.err.println(y > HEIGHT);
//        System.err.println(x < 1);
//        System.err.println(y < 1);
        if (x > HEIGHT || y > WIDTH || x < 1 || y < 1) throw new InvalidCellException();
        return board[x][y];
    }

    public Cell getCell(Cell cell) {
        return board[cell.getX()][cell.getY()];
    }

    public ArrayList<Cell> getCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            Collections.addAll(cells, board[i]);
        }
        return cells;
    }

    public void handleFireCellAffect() {
        for (Cell[] cellRow : this.board) {
            for (Cell cell : cellRow) {
                if (cell.getCellAffect().contains(CellAffects.fire)) {
                    cell.getCardOnCell().changeHealthPoint(-2);
                }
            }
        }
    }

    public void handlePoisonCell() throws InvalidCellException {
        for (Cell[] cellRow : this.board) {
            for (Cell cell : cellRow) {
                if (cell.getCellAffect().contains(CellAffects.poison)) {
                    Buff buff = new Buff(1, false, BuffActionPoison.getBuffAction(), new BuffTypePassive());
                    buff.deploy(Game.battle.getPlayer(), cell.getCardOnCell());
                }
            }
        }
    }

    public static Map generate() {
        Map map = new Map();
        for (int i = 0; i < Map.HEIGHT+1; i++) {
            for (int j = 0; j < Map.WIDTH+1; j++) {
                map.board[i][j] = new Cell(i, j);
            }
        }
        return Map.collectableGenerator(map);
    }
    private static Map collectableGenerator(Map map) {
        Random random = new Random();
        try {
            for (int i = 0; i < MAX_COLLECTABLE_ON_MAP; i++) {
                int x = 0;
                int y = 0;
                boolean cant=true;
                while (cant) {
                    x = random.nextInt(Map.HEIGHT-3);
                    y = random.nextInt(Map.WIDTH-3);
                    try {
                        if (!map.getCell(x, y).hasItem() && !map.getCell(x, y).isFull()) cant = false;
                    }catch (InvalidCellException ignored){}
                }
                x++;
                y++;
                Collectable collectable=Primary.collectables.get(random.nextInt(Primary.collectables.size()));
                System.err.println("X: "+x+" , Y: "+y);
                System.err.println("Collect me you Bitch: "+collectable.getName());
                map.getCell(x, y).setItem(collectable);
            }
        } catch (InvalidCellException e) {
            e.printStackTrace();
        }
        return map;
    }

    public  Cell getRandomEmptyCell(){
        Random random=new Random();
        int x = 0;
        int y = 0;
        boolean cant=true;
        while (cant) {
            x = random.nextInt(Map.HEIGHT-3)+1;
            y = random.nextInt(Map.WIDTH-3)+1;
            try {
                if (!this.getCell(x, y).hasItem() && !this.getCell(x, y).hasFlag() && !this.getCell(x, y).isFull()) cant = false;
            }catch (InvalidCellException ignored){}
        }
        Cell returnCell=null;
        try {
            returnCell=this.getCell(x,y);
        } catch (InvalidCellException e) {
            e.printStackTrace();
        }
        return returnCell;
    }

    public Cell[][] getBoard() {
        return board;
    }
}
