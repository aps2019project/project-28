package Model.Map;

import Controller.Game;
import Model.account.Collection;
import Model.card.spell.Buff.Buff;
import Model.card.spell.Buff.BuffActions.BuffActionPoison;
import Model.card.spell.BuffTypes.BuffTypePassive;
import Model.item.Flag;
import exeption.InvalidCellException;

import java.util.ArrayList;
import java.util.Collections;

public class Map {
    private static ArrayList<Map> maps;
    public static final int HEIGHT = 5;
    public static final int WIDTH = 9;
    private Cell[][] board = new Cell[Map.HEIGHT+1][Map.WIDTH+1];
    private ArrayList<Flag> flags;

    public static int getManhattanDistance(Cell cell1, Cell cell2) {
        return Math.abs(cell1.getX()-cell2.getX())+Math.abs(cell1.getY()-cell2.getY());
    }

    public Cell[] getCellsInDistance(Cell cell , int distance){
        ArrayList<Cell> cells = new ArrayList<>() ;
        for (Cell[] cel1 : this.board){
            for (Cell cel : cel1){
                if (Map.getManhattanDistance(cell , cel) == distance) cells.add(cel) ;
            }
        }
        Cell[] cellsArray = new Cell[cells.size()] ;
        for (int i = 0; i < cells.size(); i++) {
            cellsArray[i] = cells.get(i) ;
        }
        return cellsArray ;
    }

    public static int getRadiusDistance(Cell cell1, Cell cell2) {
        return Integer.max(Math.abs(cell1.getX()-cell2.getX()),Math.abs(cell1.getY()-cell2.getY()));

    }

    public static Map generate() {
        Map map = new Map();
        for(int i = 0 ; i < Map.HEIGHT ; i++){
            for(int j = 0 ; j < Map.WIDTH ; j++){
                map.getBoard()[i][j] = new Cell(i, j);
            }
        }
        return map;
    }

    public Cell getCell(int x, int y) throws InvalidCellException {
        if(x>WIDTH || y>HEIGHT || x<1 || y<1)throw new InvalidCellException();
        return board[x][y];
    }
    public Cell getCell(Cell cell){
        return board[cell.getX()][cell.getY()];
    }

    public ArrayList<Cell> getCells(){
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i =0 ; i < board.length ; i++) {
            Collections.addAll(cells, board[i]);
        }
        return cells ;
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

    public void handlePoisonCell() throws InvalidCellException{
        for (Cell[] cellRow : this.board) {
            for (Cell cell : cellRow) {
                if (cell.getCellAffect().contains(CellAffects.poison)) {
                    Buff buff = new Buff(1 , false , BuffActionPoison.getBuffAction() , new BuffTypePassive());
                    buff.deploy(Game.battle.getPlayer() , cell.getCardOnCell());
                }
            }
        }
    }

    public Cell[][] getBoard() {
        return board;
    }
}
