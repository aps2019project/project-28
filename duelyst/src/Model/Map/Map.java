package Model.Map;

import Controller.Game;
import Controller.menu.Battle;
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
    public static final int BALA_PAEEN_Y = 5;
    public static final int CHAP_RAST_X = 9;
    public static final int FIRST_HERO_X = 0;
    public static final int FIRST_HERO_Y = 2;
    public static final int SECOND_HERO_X = 8;
    public static final int SECOND_HERO_Y = 2;
    public static final int MAX_COLLECTABLE_ON_MAP = 3;


    private Cell[][] board = new Cell[Map.CHAP_RAST_X + 1][Map.BALA_PAEEN_Y + 1];
    private ArrayList<Flag> flags;
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
        cellsArray=cells.toArray(cellsArray);
        return cellsArray;
    }

    public static int getRadiusDistance(Cell cell1, Cell cell2) {
        if (cell1 == null || cell2 == null) return Integer.MAX_VALUE;
        return Integer.max(Math.abs(cell1.getX() - cell2.getX()), Math.abs(cell1.getY() - cell2.getY()));
    }

    public Cell getCell(int x, int y) throws InvalidCellException {
        if (x >= CHAP_RAST_X || y >= BALA_PAEEN_Y || x < 0 || y < 0) throw new InvalidCellException();
        return board[x][y];
    }

    public Cell getCell(Cell cell) throws InvalidCellException {
        return getCell(cell.getX(),cell.getY());
    }

    public ArrayList<Cell> getCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            Collections.addAll(cells, board[i]);
        }
        return cells;
    }

    public static Map generate() {
        Map map = new Map();
        for (int i = 0; i < Map.CHAP_RAST_X; i++) {
            for (int j = 0; j < BALA_PAEEN_Y; j++) {
                map.board[i][j] = new Cell(i, j);
            }
        }
        return Map.collectableGenerator(map);
    }
    private static Map collectableGenerator(Map map) {
        Random random = new Random();
            for (int i = 0; i < MAX_COLLECTABLE_ON_MAP; i++) {
                Cell cell=map.getRandomEmptyCell();
                Collectable collectable=Primary.collectables.get(random.nextInt(Primary.collectables.size()));
                cell.setItem(collectable);
            }
        return map;
    }

    public  Cell getRandomEmptyCell(){
        Random random=new Random();
        int x = 0;
        int y = 0;
        boolean cant=true;
        while (cant) {
            x = random.nextInt(Map.CHAP_RAST_X);
            y = random.nextInt(Map.BALA_PAEEN_Y);
            try {
                if (!this.getCell(x, y).hasItem() && !this.getCell(x, y).hasFlag() && !this.getCell(x, y).isFull()) cant = false;
                if((x==Map.FIRST_HERO_X && y==Map.FIRST_HERO_Y) || (x==SECOND_HERO_X || x==SECOND_HERO_Y))cant=true;
            }catch (InvalidCellException ignored){}
        }
        Cell returnCell=null;
        try {
            returnCell=this.getCell(x,y);
        } catch (InvalidCellException ignored) {ignored.printStackTrace();}
        return returnCell;
    }
}
