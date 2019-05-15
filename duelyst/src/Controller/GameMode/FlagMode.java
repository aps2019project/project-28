package Controller.GameMode;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Account;
import Model.account.Player;
import Model.card.hermione.Hermione;

public class FlagMode implements GameMode {
    private static final int prize = 1000;

    @Override
    public boolean checkState() {
        for(int i=0;i<2;i++){
            if(Game.accounts[i].getPlayer().hasFlag())
                Game.accounts[i].getPlayer().setFlagInteger( Game.accounts[i].getPlayer().getFlagInteger() + 1);
            else
                Game.accounts[i].getPlayer().setFlagInteger(0);

            if(Game.accounts[i].getPlayer().getFlagInteger() >= 6)
                return true;
        }
        return false;
    }

    @Override
    public void handleWin() {
        System.err.println();
        for (int i = 0; i < 2; i++) {
            if (Game.accounts[i].getPlayer().getFlagInteger() >=6) {
                Game.accounts[i].setMoney(Game.accounts[i].getMoney() + prize);
                Game.accounts[i].setWins(Game.accounts[i].getWins() + 1);
//                return;
            }
        }
        Account.save();
    }

    @Override
    public Map generateMap() {
        Map map=Map.generate();
        map.getRandomEmptyCell().setFlag(true);
        return map;
    }

    @Override
    public void getFlag(Player player, Hermione hermione, Cell cell) {
        if(!cell.hasFlag())return;
        player.setFlag(true);
        player.setFlagInteger(Battle.getMenu().getTurn());
        hermione.setFlag(true);
        cell.setFlag(false);
    }

//    @Override
//    public void generateMap() throws InvalidCellException, CellIsFullException {
////        GameMode.CollectableGenerator();
//
//        Random random = new Random();
//        int xf = random.nextInt(Map.HEIGHT);
//        int yf = random.nextInt(Map.WIDTH);
//
//        Battle.getMenu().getMap().getCell(xf, yf).setFlag(true);
//  }
}



