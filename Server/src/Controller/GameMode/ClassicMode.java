package Controller.GameMode;

import Controller.Game;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Account;
import Model.account.player.Player;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;

public class ClassicMode implements GameMode {

    private final static int prize = 500;

    @Override
    public boolean checkState() {
        if (Game.getAccount(0).getPlayer().getDeck().getHero() == null) return true;
        if (Game.getAccount(1).getPlayer().getDeck().getHero() == null) return true;
        return false;
    }

    @Override
    public void handleWin() {
        if(Game.getAccount(0).getPlayer().getDeck().getHero() == null){
            Game.getAccount(1).setWins(Game.getAccount(1).getWins() + 1);
            Game.getAccount(1).setMoney(Game.getAccount(1).getMoney() + ClassicMode.prize);
        }
        else if(Game.getAccount(1).getPlayer().getDeck().getHero() == null){
            Game.getAccount(0).setWins(Game.getAccount(0).getWins() + 1);
            Game.getAccount(0).setMoney(Game.getAccount(0).getMoney() + ClassicMode.prize);
        }
        //Account.save();
    }

    @Override
    public Map generateMap() {
        return Map.generate();
    }

    @Override
    public void getFlag(Player player, Hermione hermione, Cell cell) {/*ignored*/}

    @Override
    public void handleDeath(Player player, Minion minion) {/*ignored*/}


}

