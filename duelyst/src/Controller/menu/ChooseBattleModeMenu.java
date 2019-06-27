package Controller.menu;

//it is finished


import Controller.GameMode.ClassicMode;
import Controller.GameMode.Domination;
import Controller.GameMode.FlagMode;
import Controller.GameMode.GameMode;
import View.MenuHandler;

public class ChooseBattleModeMenu extends Menu {
    private static ChooseBattleModeMenu menu;
    private ChooseBattleModeMenu( String name) {
        super(name);
    }


//    @Override
//    protected void init() {
//        super.init();
//    }

    public static ChooseBattleModeMenu getMenu() {
        if(ChooseBattleModeMenu.menu==null){
            ChooseBattleModeMenu.menu=new ChooseBattleModeMenu("BattleMode");
        }
        return menu;
    }


    public void setMode(int mode) {
        System.err.println("debug");
        switch (mode){
            case 3:
                Battle.getMenu().setGameMode(new Domination());
                MenuHandler.setCurrentMenu(this.enter(GameModeMenu.getMenu()));
                break;
            case 2:
                Battle.getMenu().setGameMode(new FlagMode());
                MenuHandler.setCurrentMenu(this.enter(GameModeMenu.getMenu()));
                break;
            case 1:
                Battle.getMenu().setGameMode(new ClassicMode());
                MenuHandler.setCurrentMenu(this.enter(GameModeMenu.getMenu()));
                break;
            default:
                System.out.println("please Enter a Number between 1 and 3");
        }
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4)mode [mode number]");
    }
}
