package Controller.GameMode;

import exeption.CellIsFullException;
import exeption.InvalidCellException;

public interface GameMode {

    boolean checkState();
    void handleWin();
    void mapGenerator() throws InvalidCellException, CellIsFullException;

}
