package Controller.menu;

import Controller.GameMode.ClassicMode;
import Controller.GameMode.Domination;
import Controller.GameMode.FlagMode;
import View.MenuHandler;

public class GameModeMenu extends Menu {
    private static GameModeMenu menu;
    private GameModeMenu(String name) {
        super(name);
    }


//    @Override
//    protected void init() {
//        super.init();
//    }

    public static GameModeMenu getMenu(){
        if(GameModeMenu.menu==null){
            GameModeMenu.menu=new GameModeMenu("GameModeMenu");
        }
        return menu;
    }
}
