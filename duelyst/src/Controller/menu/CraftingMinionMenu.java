package Controller.menu;


public class CraftingMinionMenu extends Menu {
    private static CraftingMinionMenu menu;
    private CraftingMinionMenu(String name) {
        super(name);
    }

    public static CraftingMinionMenu getMenu(){
        if(CraftingMinionMenu.menu==null){
            CraftingMinionMenu.menu=new CraftingMinionMenu("CraftingMinion");
        }
        return menu;
    }

}
