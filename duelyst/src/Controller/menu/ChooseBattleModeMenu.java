package Controller.menu;

//it is finished


public class ChooseBattleModeMenu extends Menu {
    private static ChooseBattleModeMenu menu;
    private ChooseBattleModeMenu( String name) {
        super(name);
    }


//    @Override
//    protected void init() {
//        super.init();
//    }

    public static Menu getMenu() {
        if(ChooseBattleModeMenu.menu==null){
            ChooseBattleModeMenu.menu=new ChooseBattleModeMenu("BattleMode");
        }
        return menu;
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4)mode [mode number]");
    }
}
