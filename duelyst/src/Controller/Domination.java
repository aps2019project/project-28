package Controller;

import Controller.menu.GameMode;
import Controller.menu.Menu;
import Model.Map.Map;

public class Domination implements GameMode {

    @Override
    public boolean checkState() {
        return false;
    }

    @Override
    public Map mapGenerator() {
        return null;
    }
}