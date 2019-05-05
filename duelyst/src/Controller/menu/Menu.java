package Controller.menu;

import Model.account.Account;

import java.util.ArrayList;

public abstract class Menu {

    protected Account account;
    private String name;
    private Menu parentMenu;
    private ArrayList<Menu> subMenus;
    private ArrayList<OnMenuClickedListener> menuPresenters;
    private ArrayList<String>patterns;



    public Menu(Menu parentMenu) {
        this.parentMenu = parentMenu;
        this.subMenus=new ArrayList<>();
        this.patterns=new ArrayList<>();
    }


    public void addPattern(String pattern){
        this.patterns.add(pattern);
    }
    public void addSubMenu(Menu subMenu){
        this.subMenus.add(subMenu);
    }
    public void addMenuClickListener(OnMenuClickedListener presenter){
        this.menuPresenters.add(presenter);
    }

    public boolean allowsCommand(String command){
        for (String pattern : this.patterns) {
            if(command.matches(pattern))return true;
        }
        return false;
    }

    public abstract void help();

    public void init(){
        for (OnMenuClickedListener presenter : this.menuPresenters) {
            presenter.show();
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

    protected Account getAccount(){
        return this.account;
    }
}
