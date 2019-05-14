package Controller.GameMode;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Map;
import Model.Primary;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.item.Collectable;
import exeption.CellIsFullException;
import exeption.InvalidCellException;

import java.util.Random;

public class Domination implements GameMode {
    static int numberOfFlags;
    private final static int prize = 1500;

    @Override
    public boolean checkState() {

        return false;
    }

    @Override
    public void handleWin() {
        int i = 0;
        for (Card card:
                Game.accounts[0].getPlayer().getDeck().getCards()) {
            if(card instanceof Hero || card instanceof Minion){
                if (((Hermione)card).hasFlag()){
                    i ++;
                }
            }
        }
        if( i > numberOfFlags/2){
            Game.accounts[0].setWins(Game.accounts[0].getWins() + 1);
            Game.accounts[0].setMoney(Game.accounts[0].getMoney() + Domination.prize);
        }

        int j =0;
        for (Card card:
                Game.accounts[1].getPlayer().getDeck().getCards()) {
            if(card instanceof Hero || card instanceof Minion){
                if (((Hermione)card).hasFlag()){
                     j++;
                }
            }
        }
        if(j > numberOfFlags/2) {
            Game.accounts[1].setWins(Game.accounts[1].getWins() + 1);
            Game.accounts[1].setMoney(Game.accounts[1].getMoney() + Domination.prize);
        }
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