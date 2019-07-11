package Controller.GameMode;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Account;
import Model.account.player.Player;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;

public class FlagMode implements GameMode {
    private static final int prize = 1000;

    @Override
    public boolean checkState() {
        for(int i=0;i<2;i++){
            if(Game.getAccount(i).getPlayer().hasFlag())
                Game.getAccount(i).getPlayer().setFlagInteger( Game.getAccount(i).getPlayer().getFlagInteger() + 1);
            else
                Game.getAccount(i).getPlayer().setFlagInteger(0);

            if(Game.getAccount(i).getPlayer().getFlagInteger() >= 6)
                return true;
        }
        return false;
    }

    @Override
    public void handleWin() {
        for (int i = 0; i < 2; i++) {
            if (Game.getAccount(i).getPlayer().getFlagInteger() >=6) {
                Game.getAccount(i).setMoney(Game.getAccount(i).getMoney() + prize);
                Game.getAccount(i).setWins(Game.getAccount(i).getWins() + 1);
                Battle.getMenu().winner = Game.getAccount(i);
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

    @Override
    public void handleDeath(Player player, Minion minion) {
        if(!minion.hasFlag())return;
        player.setFlagInteger(0);
        player.setFlag(false);
    }

}



