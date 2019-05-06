package Controller.menu;

public class StoryModeMenu extends Menu {
    private static StoryModeMenu menu;
    private StoryModeMenu(String name) {
        super(name);
    }

    public static StoryModeMenu getMenu(){
        if(StoryModeMenu.menu==null){
            StoryModeMenu.menu=new StoryModeMenu("StoryModeMenu");
        }
        return menu;
    }

}
