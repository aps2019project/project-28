package Controller.menu;


public class CraftingSpellMenu extends Menu {
    private static CraftingSpellMenu menu;
    private CraftingSpellMenu(String name) {
        super(name);
    }

    public static CraftingSpellMenu getMenu(){
        if(CraftingSpellMenu.menu==null){
            CraftingSpellMenu.menu=new CraftingSpellMenu("CraftingSpell");
        }
        return menu;
    }

}
