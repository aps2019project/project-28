package Controller.GameMode;

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
       /* Game.accounts[0].setWins(Game.accounts[0].getWins() + 1);
        Game.accounts[0].setMoney(Game.accounts[0].getMoney() + FlagMode.prize);

        Game.accounts[1].setMoney(Game.accounts[1].getMoney() + FlagMode.prize);
        Game.accounts[1].setWins(Game.accounts[1].getWins() + 1);*/
    }

    @Override
    public void mapGenerator() throws InvalidCellException, CellIsFullException {

        for (Collectable collectable:
                Primary.collectables) {
            Random random = new Random();
            if(random.nextInt(Map.HEIGHT) == 0){
                int x = random.nextInt(Map.HEIGHT);
                int y = random.nextInt(Map.WIDTH);

                Battle.getMenu().getMap().getCell(x, y).setCollectable(collectable);
            }
        }

        Random random = new Random();
        int xf = random.nextInt(Map.HEIGHT);
        int yf = random.nextInt(Map.WIDTH);

        Battle.getMenu().getMap().getCell(xf, yf).setFlag(true);
    }
}
