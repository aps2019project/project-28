package Model.account;

import Controller.Game;
import Model.Map.Map;
import Model.card.hermione.Hermione;

import java.util.ArrayList;
import java.util.Collections;

public class AI extends Account {
    public AI() {
        super("AI", "itsAI", "imAnAIgirlInAnAIWorld");
    }
    public String play(){
        Map map = Game.battle.getMap();
        Player player = Game.battle.getMe(this) ;
        ArrayList<Hermione> myPlayers = new ArrayList<>() ;
        myPlayers.add(player.getDeck().getHero()) ;
        Collections
    }
}
