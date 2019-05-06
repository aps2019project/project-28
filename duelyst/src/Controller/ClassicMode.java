package Controller;

import Controller.menu.GameMode;
import Controller.menu.Menu;
import Model.Map.Map;
import Model.card.hermione.Hero;

public class ClassicMode implements GameMode {


    @Override
    public boolean checkState() {
        if(Game.accounts[0].getPlayer().getDeck().getHero()==null)return true;

        return false;
    }

    @Override
    public Map mapGenerator() {
        return null;
    }
}

