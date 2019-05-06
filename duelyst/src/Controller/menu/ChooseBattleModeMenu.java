package Controller.menu;

//it is finished


public class ChooseBattleModeMenu extends Menu {
    private static ChooseBattleModeMenu menu;
    private ChooseBattleModeMenu( String name) {
        super(name);
    }

    public static Menu getMenu() {
        if(ChooseBattleModeMenu.menu==null){
            ChooseBattleModeMenu.menu=new ChooseBattleModeMenu("ChooseBattleModeMenu");
        }
        return menu;
    }

}
