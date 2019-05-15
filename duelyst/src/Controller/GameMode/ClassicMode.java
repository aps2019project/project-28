package Controller.GameMode;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Map;
import Model.Primary;
import Model.account.Account;
import Model.item.Collectable;
import exeption.CellIsFullException;
import exeption.InvalidCellException;

import java.util.Random;

public class ClassicMode implements GameMode {

    private final static int prize = 500;

    @Override
    public boolean checkState() {
        if (Game.accounts[0].getPlayer().getDeck().getHero() == null) return true;
        if (Game.accounts[1].getPlayer().getDeck().getHero() == null) return true;
        return false;
    }

    @Override
    public void handleWin() {
        if(Game.accounts[0].getPlayer().getDeck().getHero() == null){
            Game.accounts[1].setWins(Game.accounts[1].getWins() + 1);
            Game.accounts[1].setMoney(Game.accounts[1].getMoney() + ClassicMode.prize);
        }
        else if(Game.accounts[1].getPlayer().getDeck().getHero() == null){
            Game.accounts[0].setWins(Game.accounts[0].getWins() + 1);
            Game.accounts[0].setMoney(Game.accounts[0].getMoney() + ClassicMode.prize);
        }
        Account.save();
    }

    @Override
    public Map generateMap() {
        return Map.generate();
    }


}

