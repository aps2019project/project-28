package Controller.GameMode;

import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Player;
import Model.card.hermione.Hermione;

public interface GameMode {

    boolean checkState();
    void handleWin();
    Map generateMap();

    void getFlag(Player player,Hermione hermione, Cell cell);
}
