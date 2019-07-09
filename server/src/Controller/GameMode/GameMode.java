package Controller.GameMode;

import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Account;
import Model.account.player.Player;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;

public interface GameMode {

    boolean checkState();
    void handleWin();
    Map generateMap();

    void getFlag(Player player,Hermione hermione, Cell cell);

    void handleDeath(Player player, Minion minion);
}
