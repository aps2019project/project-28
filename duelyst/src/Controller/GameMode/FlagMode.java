package Controller.GameMode;

import Controller.Game;
import Model.Map.Map;

public class FlagMode implements GameMode {
    private static final int prize = 1000;

    @Override
    public boolean checkState() {
        return false;
    }

    @Override
    public void handleWin() {
        Game.accounts[0].setWins(Game.accounts[0].getWins() + 1);
        Game.accounts[0].setMoney(Game.accounts[0].getMoney() + FlagMode.prize);

        Game.accounts[1].setMoney(Game.accounts[1].getMoney() + FlagMode.prize);
        Game.accounts[1].setWins(Game.accounts[1].getWins() + 1);
    }

    @Override
    public Map mapGenerator() {
        return null;
    }
}
