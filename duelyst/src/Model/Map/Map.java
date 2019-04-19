package Model.Map;

import Model.item.Flag;

import java.util.ArrayList;

public class Map {
    private static ArrayList<Map> maps;
    private static final int HEIGHT = 5;
    private static final int WIDTH = 9;
    private Cell[][] board=new Cell[Map.HEIGHT][Map.WIDTH];
    private ArrayList<Flag> flags;

    public static int getManhattanDistance(Cell cell1, Cell cell2) {
        return Math.abs(cell1.getX()-cell2.getX())+Math.abs(cell1.getY()-cell2.getY());
    }
    public Cell getCell(int x, int y){
        return board[x][y];
    }

}
