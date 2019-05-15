package Controller.GameMode;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.Map;
import Model.Primary;
import Model.account.Account;
import Model.account.Player;
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

        if(Battle.getMenu().getPlayer().getFlagInteger()>numberOfFlags/2)return true;
        if(Battle.getMenu().getEnemyPlayer().getFlagInteger()>numberOfFlags/2)return true;
        return false;
    }

    @Override
    public void handleWin() {
        for(int i=0;i<2 ;i++){
            if(Game.accounts[i].getPlayer().getFlagInteger()>numberOfFlags/2){
                Game.accounts[i].setMoney(Game.accounts[i].getMoney() + prize);
                Game.accounts[i].setWins(Game.accounts[i].getWins() + 1);
            }
        }
        Account.save();
    }

    @Override
    public Map generateMap() {
        Map map=Map.generate();
        Random random = new Random();
        while (numberOfFlags == 1 || numberOfFlags == 0){
            numberOfFlags = random.nextInt(5);
        }
        numberOfFlags=numberOfFlags*2-1;

        for (int i = 0; i < numberOfFlags ; i++){
            Cell cell=map.getRandomEmptyCell();
                cell.setFlag(true);
        }
        return map;
    }

    @Override
    public void getFlag(Player player,Hermione hermione, Cell cell) {
        if(!cell.hasFlag())return;
        player.setFlag(true);
        player.setFlagInteger(player.getFlagInteger()+1);
        hermione.setFlag(true);
        cell.setFlag(false);

    }
}