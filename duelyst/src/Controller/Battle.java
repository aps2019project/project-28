package Controller;

import Model.Map.Map;
import Model.account.Account;
import Model.account.Player;
import Model.card.spell.Spell;

import java.util.ArrayList;

public class Battle {
    private Map map;
    private Player[] player=new Player[2];
    private int turn = 0 ;
    private ArrayList<Spell> ongoingSpells = new ArrayList<>();

    public Player getEnemy(Account me) {
        if (player[0].getUser().equals(me)) return player[1];
        return player[0];

    }

    public Player getMe(Account me){
        if(player[0].getUser().equals(me))return player[0];
        return player[1];

    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer(){
        return player[turn] ;
    }

    public void nextTurn(){turn++ ; }

    public int getTurn() {return turn%2 ; }
    public int getOriginalTurn(){ return this.turn; }
}
