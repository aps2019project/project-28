package Controller.menu;


public class MainMenu extends Menu {
    private static MainMenu menu;
    private  MainMenu(String name) {
        super(name);
    }

    public static MainMenu getMenu(){
        if(MainMenu.menu==null){
            MainMenu.menu=new MainMenu("MainMenu");
        }
        return menu;
    }

    @Override
    public boolean init(Menu parentMenu) {
        return super.init(parentMenu);

    }
}
