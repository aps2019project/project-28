package Controller.GameMode;

import Controller.menu.Battle;
import Model.Map.Map;
import Model.Primary;
import Model.item.Collectable;
import exeption.CellIsFullException;
import exeption.InvalidCellException;

import java.util.Random;

public class Domination implements GameMode {
    private final static int prize = 1500;

    @Override
    public boolean checkState() {

        return false;
    }

    @Override
    public void handleWin() {
        /*Game.accounts[0].setWins(Game.accounts[0].getWins() + 1);
        Game.accounts[0].setMoney(Game.accounts[0].getMoney() + Domination.prize);

        Game.accounts[1].setWins(Game.accounts[1].getWins() + 1);
        Game.accounts[1].setMoney(Game.accounts[1].getMoney() + Domination.prize);*/
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
        int numberOfFlags = 0;
        while (numberOfFlags == 1 || numberOfFlags == 0){
            numberOfFlags = random.nextInt(10);
        }

        for (int i = 0; i < numberOfFlags ; i++){
            int x = random.nextInt(Map.HEIGHT);
            int y = random.nextInt(Map.WIDTH);

            Battle.getMenu().getMap().getCell(x, y).setFlag(true);
        }
    }
}