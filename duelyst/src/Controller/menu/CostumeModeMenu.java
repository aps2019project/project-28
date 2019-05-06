package Controller.menu;

public class CostumeModeMenu extends Menu {
    private static CostumeModeMenu menu;
    private CostumeModeMenu(String name) {
        super(name);
    }
    public static CostumeModeMenu getMenu(){
        if(CostumeModeMenu.menu==null){
            menu=new CostumeModeMenu("CostumeMenu");
        }
        return menu;
    }

}
