package Controller.GameMode;

import Model.Map.Map;

public interface GameMode {

    boolean checkState();
    void handleWin();
    Map generateMap();
}
