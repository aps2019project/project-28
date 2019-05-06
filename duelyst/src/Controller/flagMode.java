package Controller;

import Controller.menu.GameMode;
import Model.Map.Map;

public class flagMode implements GameMode {

    @Override
    public boolean checkState() {

        return false;
    }

    @Override
    public Map mapGenerator() {
        return null;
    }
}
