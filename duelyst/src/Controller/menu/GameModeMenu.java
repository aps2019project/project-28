package Controller.menu;

public class GameModeMenu extends Menu {
    private static GameModeMenu menu;
    private GameModeMenu(String name) {
        super(name);
    }


//    @Override
//    protected void buildScene() {
//        super.buildScene();
//    }

    public static GameModeMenu getMenu(){
        if(GameModeMenu.menu==null){
            GameModeMenu.menu=new GameModeMenu("GameModeMenu");
        }
        return menu;
    }
}
