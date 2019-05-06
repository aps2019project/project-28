package Controller.GameMode;

import Model.Map.Map;
import Model.account.Account;

public interface GameMode {

    boolean checkState();
    void handleWin();
    Map mapGenerator();

}
