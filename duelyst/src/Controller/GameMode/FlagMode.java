package Controller.GameMode;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Map;
import Model.Primary;
import Model.item.Collectable;
import exeption.CellIsFullException;
import exeption.InvalidCellException;

import java.util.Random;

public class FlagMode implements GameMode {
    private static final int prize = 1000;

    @Override
    public boolean checkState() {
        return false;
    }

    @Override
    public void handleWin() {
        if(Game.accounts[0].getPlayer().getDeck().getHero().hasFlag()){
            Game.accounts[0].getPlayer().getDeck().getHero().setNumberOfFlags( Game.accounts[0].getPlayer().getDeck().getHero().getNumberOfFlags() + 1);
        }
        else {
            Game.accounts[0].getPlayer().getDeck().getHero().setNumberOfFlags(0);
        }

        if(Game.accounts[0].getPlayer().getDeck().getHero().getNumberOfFlags() >= 6){
            Game.accounts[0].setWins(Game.accounts[0].getWins() + 1);
            Game.accounts[0].setMoney(Game.accounts[0].getMoney() + FlagMode.prize);
        }

        if(Game.accounts[1].getPlayer().getDeck().getHero().hasFlag()){
            Game.accounts[1].getPlayer().getDeck().getHero().setNumberOfFlags(Game.accounts[1].getPlayer().getDeck().getHero().getNumberOfFlags() + 1);
        }
        else {
            Game.accounts[1].getPlayer().getDeck().getHero().setNumberOfFlags(1);
        }
        if(Game.accounts[1].getPlayer().getDeck().getHero().getNumberOfFlags() >= 6) {
            Game.accounts[1].setMoney(Game.accounts[1].getMoney() + FlagMode.prize);
            Game.accounts[1].setWins(Game.accounts[1].getWins() + 1);
        }
    }

    @Override
    public Map mapGenerator() {
        return null;
    }

//    @Override
//    public void mapGenerator() throws InvalidCellException, CellIsFullException {
////        GameMode.CollectableGenerator();
//
//        Random random = new Random();
//        int xf = random.nextInt(Map.HEIGHT);
//        int yf = random.nextInt(Map.WIDTH);
//
//        Battle.getMenu().getMap().getCell(xf, yf).setFlag(true);
//  }
}



