package Controller;

import Model.Map.Map;
import Model.account.Account;
import Model.account.Player;

public class Battle {
    private Map map;
    private Player[] player=new Player[2];

    public Player getEnemy(Account me){
        if(player[0].getUser().equals(me))return player[1];
        return player[0];
    }
    public Player getMe(Account me){
        if(player[0].getUser().equals(me))return player[0];
        return player[1];

    }

    public Map getMap() {
        return map;
    }
}
