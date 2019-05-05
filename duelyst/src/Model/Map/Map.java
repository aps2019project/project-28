package Model.Map;

import Model.item.Flag;
import exeption.InvalidCellException;

import java.util.ArrayList;

public class Map {
    private static ArrayList<Map> maps;
    public static final int HEIGHT = 5;
    public static final int WIDTH = 9;
    private Cell[][] board=new Cell[Map.HEIGHT+1][Map.WIDTH+1];
    private ArrayList<Flag> flags;

    public static int getManhattanDistance(Cell cell1, Cell cell2) {
        return Math.abs(cell1.getX()-cell2.getX())+Math.abs(cell1.getY()-cell2.getY());
    }
    public static int getRadiusDistance(Cell cell1, Cell cell2) {
        return Integer.max(Math.abs(cell1.getX()-cell2.getX()),Math.abs(cell1.getY()-cell2.getY()));

    }

    public static Map generate() {
        // TODO: 5/5/19 generate Map
    }


    public Cell getCell(int x, int y) throws InvalidCellException {
        if(x>WIDTH || y>HEIGHT || x<1 || y<1)throw new InvalidCellException();
        return board[x][y];
    }
    public Cell getCell(Cell cell){
        return board[cell.getX()][cell.getY()];
    }

    public void applyFireCellAffect() {
        for (Cell[] cellRow : this.board) {
            for (Cell cell : cellRow) {
                if (cell.getCellAffect() == CellAffects.fire) {
                    cell.getCardOnCell().changeHealthPoint(-2);
                }
            }
        }
    }

}
