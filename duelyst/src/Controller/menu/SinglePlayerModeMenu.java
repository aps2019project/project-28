package Controller.menu;

public class SinglePlayerModeMenu extends Menu {
    private static SinglePlayerModeMenu menu;
    private SinglePlayerModeMenu(String name) {
        super(name);
    }
    public static SinglePlayerModeMenu getMenu(){
        if(SinglePlayerModeMenu.menu==null){
            SinglePlayerModeMenu.menu=new SinglePlayerModeMenu("SinglePlayerModeMenu");
        }
        return menu;
    }

}
