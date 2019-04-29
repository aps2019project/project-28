package Controller.menu;

import com.sun.jdi.ArrayReference;

import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class Menu {

    private String name;
    private Menu parentMenu;
    private ArrayList<Menu> subMenus;
    private ArrayList<OnMenuPresentedListener> menuPresenters;
    private ArrayList<Pattern>patterns;



    public Menu(Menu parentMenu) {
        this.parentMenu = parentMenu;
        this.subMenus=new ArrayList<>();
        this.patterns=new ArrayList<>();
    }


    public void addPattern(Pattern pattern){
        this.patterns.add(pattern);t
    }
    public void addSubMenu(Menu subMenu){
        this.subMenus.add(subMenu);
    }
    public void addMenuPresenteListener(OnMenuPresentedListener presenter){
        this.menuPresenters.add(presenter);
    }

    public abstract boolean allowsCommand(String command);

    public abstract void help();

    public void init(){
        for (OnMenuPresentedListener menuPresenter : this.menuPresenters) {
            menuPresenter.onMenuPresented();
        }
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public Menu getMenuFromSubMenus(String name){
        for (Menu subMenu : this.subMenus) {
            if(subMenu.name.equals(name))return subMenu;
        }
        return null;
    }
}
