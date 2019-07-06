package Model.Map;

import Model.Primary;
import Model.item.Collectable;
import Model.item.Flag;
import exeption.InvalidCellException;
import org.spockframework.util.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class Map {
    public static final int HEIGHT =5;
    public static final int WHIDTH =9;
    public static final int FIRST_HERO_X=0;
    public static final int FIRST_HERO_Y=2;
    public static final int SECOND_HERO_X=8;
    public static final int SECOND_HERO_Y=2;
    public static final int MAX_COLLECTABLE_ON_MAP=3;

    private static final int[]dx={-1,0,1,0};
    private static final int[]dy={0,-1,0,1};

    private Cell[][] board = new Cell[Map.WHIDTH + 1][Map.HEIGHT + 1];
    private ArrayList<Flag> flags;


    public static int getManhattanDistance(Cell start, Cell end) {
        return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
    }
    public Cell[] getCellsInDistance(Cell cell, int distance) {
        ArrayList<Cell> retVal = new ArrayList<>();
        for (Cell[] cells : this.board) {
            for (Cell cel : cells) {
                if (cel == null) continue;
                if (Map.getManhattanDistance(cell, cel) == distance) retVal.add(cel);
            }
        }
        Cell[] cellsArray = new Cell[retVal.size()];
        cellsArray=retVal.toArray(cellsArray);
        return cellsArray;
    }
    public static int getRadiusDistance(Cell cell1, Cell cell2) {
        if (cell1 == null || cell2 == null) return Integer.MAX_VALUE;
        return Integer.max(Math.abs(cell1.getX() - cell2.getX()), Math.abs(cell1.getY() - cell2.getY()));
    }


    public static Map generate() {
        Map map = new Map();
        for (int i = 0; i < Map.WHIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
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

    public ArrayList<Cell> getCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            Collections.addAll(cells, board[i]);
        }
        return cells;
    }
    public  Cell getRandomEmptyCell(){
        Random random=new Random();
        int x = 0;
        int y = 0;
        boolean cant=true;
        while (cant) {
            x = random.nextInt(Map.WHIDTH);
            y = random.nextInt(Map.HEIGHT);
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
    public Cell getCell(int x, int y) throws InvalidCellException {
        if (x >= WHIDTH || y >= HEIGHT || x < 0 || y < 0) throw new InvalidCellException();
        return board[x][y];
    }
    public Cell getCell(Cell cell) throws InvalidCellException {
        return getCell(cell.getX(),cell.getY());
    }

    public ArrayList<Cell> getPath(Cell start,Cell end,int maxTurns){
        ArrayList<Cell>retVal=this.findPath(start,end,0,maxTurns);
        if(retVal!=null)
            Collections.reverse(retVal);
        return retVal;
    }
    private ArrayList<Cell> findPath(Cell start, Cell end,int turn,int maxTurns) {
        ArrayList<Cell>retVal;

        // break conditions
        if(turn>maxTurns)return null;
        if(start.getX()==end.getX() && start.getY()==end.getY()){
            return new ArrayList<>();
        }

        for(int i=0;i<dx.length;i++){
            try {
                Cell neighbor=this.getCell(start.getX()+dx[i],start.getY()+dy[i]);

                //bad situation
                if(neighbor.getCardOnCell()!=null)continue;


                retVal=findPath(neighbor, end,turn+1,maxTurns);

                if(retVal!=null){
                    retVal.add(neighbor);
                    return retVal;
                }
            } catch (InvalidCellException ignored) { }
        }
        return null;
    }

}
