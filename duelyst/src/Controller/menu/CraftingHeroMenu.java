package Controller.menu;


public class CraftingHeroMenu extends Menu {
    private static CraftingHeroMenu menu;
    private CraftingHeroMenu(String name) {
        super(name);
    }

    public static CraftingHeroMenu getMenu(){
        if(CraftingHeroMenu.menu==null){
            CraftingHeroMenu.menu=new CraftingHeroMenu("CraftingHero");
        }
        return menu;
    }

}
